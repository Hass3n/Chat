package minia.chatapp.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import minia.chatapp.R;
import minia.chatapp.models.Employee;

public class Employeeadapter extends RecyclerView.Adapter<Employeeadapter.MessageViewHolder> {

    ArrayList<Employee> users;
    Context context;
    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public Employeeadapter(ArrayList<Employee> users, Context icontex) {
        this.users = users;
        this.context=icontex;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from( viewGroup.getContext()).inflate(  R.layout.recycle_list_single_user, viewGroup,false);
        return new  MessageViewHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, final int i) {

        final Employee item=users.get(i);
        messageViewHolder.userNameView.setText(item.getName());
        messageViewHolder.userStatusView.setText(item.getDepartment().getName());
        // String image=item.getImage().toString();
        Picasso.with(context).load(R.drawable.user_img).placeholder(R.drawable.user_img).into(messageViewHolder.userImageView);
        if (messageViewHolder!=null)
        {

            messageViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onitemclick(item,i);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    //----RETURNING VIEW OF SINGLE HOLDER----
    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView userNameView;
        TextView userStatusView;
        ImageView  userImageView;
        View parent;
        public MessageViewHolder(View itemView) {
            super(itemView);

            userNameView=itemView.findViewById(R.id.textViewSingleListName);
            userStatusView=itemView.findViewById(R.id.textViewSingleListStatus);
            userImageView=itemView.findViewById(R.id.circleImageViewUserImage);
            parent=itemView;

        }

    }

    public interface OnItemClick
    {

        public void onitemclick(Employee data, int postion);


    }


}
