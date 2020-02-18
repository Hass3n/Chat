package minia.chatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.ocpsoft.prettytime.PrettyTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import io.reactivex.annotations.NonNull;
import minia.chatapp.R;
import minia.chatapp.models.Room;

public class EmployeeRoomsAdapter extends RecyclerView.Adapter<EmployeeRoomsAdapter.RoomsViewHolder> {

    ArrayList<Room> rooms;
    Context context;
    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public EmployeeRoomsAdapter(ArrayList<Room> rooms, Context icontex) {
        this.rooms = rooms;
        this.context=icontex;
    }

    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from( viewGroup.getContext()).inflate(  R.layout.room_item_layout, viewGroup,false);
        return new RoomsViewHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsViewHolder messageViewHolder, final int i) {

        final Room item=rooms.get(i);
        messageViewHolder.roomName.setText(item.getUser().getName());
        String lastMessage = item.getLastSentMessage();
        if (lastMessage.length() >= 30){
            messageViewHolder.lastMessage.setText(lastMessage.substring(0, 30) + " ...");
        }else{
            messageViewHolder.lastMessage.setText(lastMessage);
        }

        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        String ago = prettyTime.format(new Date(item.getLastMessageCreatedTime()));
        messageViewHolder.createdAt.setText(ago);

        if (messageViewHolder!=null)
        {
            messageViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(item,i);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    //----RETURNING VIEW OF SINGLE HOLDER----
    public class RoomsViewHolder extends RecyclerView.ViewHolder {

        TextView roomName;
        TextView lastMessage;
        TextView createdAt;
        View parent;
        public RoomsViewHolder(View itemView) {
            super(itemView);

            roomName=itemView.findViewById(R.id.txtName);
            lastMessage=itemView.findViewById(R.id.txtLastMessage);
            createdAt=itemView.findViewById(R.id.txtCreatedAt);
            parent=itemView;
        }
    }

    public interface OnItemClick
    {
        public void onItemClick(Room data, int postion);
    }


}