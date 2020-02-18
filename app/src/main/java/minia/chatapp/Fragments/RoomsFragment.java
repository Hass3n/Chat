package minia.chatapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import minia.chatapp.R;
import minia.chatapp.views.UserChatActivity;
import minia.chatapp.adapters.RoomsAdapter;
import minia.chatapp.models.Employee;
import minia.chatapp.models.Room;


public class RoomsFragment extends Fragment {
    RecyclerView roomsRecycler;
    DatabaseReference roomsDatabaseReference;
    DatabaseReference employeesDatabaseReference;
    ProgressDialog mProgressDialog;
    public RoomsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rooms, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roomsRecycler = view.findViewById(R.id.roomsRecycler);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("myDetails", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "1");

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        roomsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("rooms");
        roomsDatabaseReference.orderByChild("userId").equalTo(userId).addValueEventListener(listener);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            final ArrayList<Room> rooms = new ArrayList();
            for (DataSnapshot dmo : dataSnapshot.getChildren()){
                Room room = dmo.getValue(Room.class);
                room.setKey(dmo.getKey());
                rooms.add(room);
            }

            employeesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("employees");
            employeesDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Employee> employees = new ArrayList<>();
                    for(DataSnapshot dmo : dataSnapshot.getChildren()){
                        Employee employee = dmo.getValue(Employee.class);
                        employee.setKey(dmo.getKey());
                        employees.add(employee);
                    }

                    for (int i=0; i < rooms.size(); i++){
                        for (int j = 0; j < employees.size(); j++){
                            if (rooms.get(i).getEmployeeId() != null && rooms.get(i).getEmployeeId().equals(employees.get(j).getKey())){
                                rooms.get(i).setEmployee(employees.get(j));
                                break;
                            }
                        }
                    }

                    roomsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    RoomsAdapter adapter = new RoomsAdapter(rooms, getContext());
                    roomsRecycler.setAdapter(adapter);

                    adapter.setOnItemClick(new RoomsAdapter.OnItemClick() {
                        @Override
                        public void onItemClick(Room room, int postion) {
                            Intent intent = new Intent(getContext(), UserChatActivity.class);
                            intent.putExtra("roomKey", room.getKey());
                            intent.putExtra("userId", room.getUserId());
                            intent.putExtra("empId", room.getEmployeeId());
                            startActivity(intent);
                        }
                    });

                    mProgressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                   mProgressDialog.dismiss();
                }
            });
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }
    };
}
