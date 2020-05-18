package org.juicecode.telehlam.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.UserIds;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageViewModel;
import org.juicecode.telehlam.database.users.User;
import org.juicecode.telehlam.database.users.UserViewModel;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.user.UserRepository;
import org.juicecode.telehlam.socketio.AppSocket;
import org.juicecode.telehlam.socketio.MessageEvent;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.KeyboardManager;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView chat;
    private EditText messageField;
    private long userId;
    private long receiverId;
    private MessageChatAdapter messageChatAdapter;
    private AppSocket socket;
    private Context context;
    private String receiverLogin;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        context = getContext();

        socket = AppSocket.getInstance(Constant.baseUrl);
        final MessageViewModel messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);

        //all variables get their values
        final Context context = getContext();
        chat = view.findViewById(R.id.chat);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        chat.setLayoutManager(linearLayout);
        messageChatAdapter = new MessageChatAdapter();
        Bundle arguments = getArguments();
        User user = (User) arguments.getSerializable("user");
        receiverId = user.getId();

        SharedPreferencesRepository repository = new SharedPreferencesRepository(context);
        String userLogin = repository.getLogin();
        userId = repository.getId();
        chat.setAdapter(messageChatAdapter);
        chat.setHasFixedSize(false);
        chat.setNestedScrollingEnabled(false);
        chat.scrollToPosition(messageChatAdapter.getItemCount() - 1);
        messageField = view.findViewById(R.id.message_field);
        List<Message> messageList = new ArrayList<>();
        ImageButton sendButton = view.findViewById(R.id.send_message_button);
        TextView nameOfContact = view.findViewById(R.id.chat_name);
        nameOfContact.setText(receiverLogin);
        ImageButton goBack = view.findViewById(R.id.go_back_button);
        nameOfContact.setText(user.getLogin());

        // Getting all messages for chat
        final MessageViewModel viewModel = ViewModelProviders
                .of(this)
                .get(MessageViewModel.class);
        viewModel.getChatMessages(receiverId).observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messageChatAdapter.setMessages(messages);
                chat.scrollToPosition(messageChatAdapter.getItemCount() - 1);
            }
        });

        final UserViewModel userViewModel = ViewModelProviders
                .of(this)
                .get(UserViewModel.class);

        viewModel.getUnreadMessages(receiverId).observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                for (Message message : messages) {
                    message.setRead(true);
                    viewModel.update(message);
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageField.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    final Message message = new Message(Message.MESSAGE_OUTGOING, messageText, userId, receiverId);

                    new MessageEvent(socket).sendMessage(message);

                    UserIds ids = UserIds.getInstance(ChatFragment.this, getViewLifecycleOwner());
                    if (!ids.contains(receiverId)) {
                        new UserRepository(new RetrofitBuilder()).byId(receiverId).observe(getViewLifecycleOwner(), new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                userViewModel.insert(user);
                                // We insert message here, cuz get user byId execute in other thread
                                messageViewModel.insert(message);
                            }
                        });
                    } else {
                        messageViewModel.insert(message);
                    }

                    messageField.setText("");
                    chat.scrollToPosition(messageChatAdapter.getItemCount() - 1);
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardManager.hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}