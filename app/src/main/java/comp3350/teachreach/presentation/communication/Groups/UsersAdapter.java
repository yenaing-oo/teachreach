package comp3350.teachreach.presentation.communication.Groups;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.CardChatUserBinding;
import comp3350.teachreach.objects.interfaces.IAccount;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<IAccount> users;
    private CardChatUserBinding binding;

    public UsersAdapter(List<IAccount> users){
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CardChatUserBinding cardChatUserBinding = CardChatUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false);

        return new UserViewHolder(cardChatUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {
        userViewHolder.setUserData(users.get(position));

    }


    @Override
    public int getItemCount() {
        return users.size();
    }



    class UserViewHolder extends RecyclerView.ViewHolder{
        UserViewHolder(CardChatUserBinding cardChatUserBinding){
            super(cardChatUserBinding.getRoot());
            binding = cardChatUserBinding;
        }

        void setUserData(IAccount user){
            binding.tvName.setText(user.getUserName());
            binding.tvMajorField.setText(user.getUserMajor());
            binding.emailView.setText(user.getAccountEmail());
        }
    }

}
