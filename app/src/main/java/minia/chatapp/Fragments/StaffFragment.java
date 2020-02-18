package minia.chatapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import minia.chatapp.models.Data;
import minia.chatapp.views.EmployeeProfileActivity;
import minia.chatapp.adapters.Employeeadapter;
import minia.chatapp.R;
import minia.chatapp.models.Users;
import minia.chatapp.models.Department;
import minia.chatapp.models.Employee;


public class StaffFragment extends Fragment {

    public View mMainView;
    private RecyclerView mUsersList;
    private DatabaseReference mEmployeeDatabaseReference;
    private DatabaseReference mDepartmentDatabaseReference;
    ArrayList<Users> users;
    ArrayList<Data>data;
    ArrayList<Employee> employees;
    LinearLayoutManager linearLayoutManager;
    Employeeadapter employeeadapter;
    private long facultyId;
    ProgressDialog mProgressDialog;

    public StaffFragment(long facultyId) {
        // Required empty public constructor
        this.facultyId = facultyId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView= inflater.inflate(R.layout.fragment_staff, container, false);

        mUsersList=(RecyclerView)mMainView.findViewById(R.id.recyclerViewUsersList);
        mUsersList.setHasFixedSize(true);
        return mMainView;
    }

    public void onStart() {
        super.onStart();


        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        mEmployeeDatabaseReference= FirebaseDatabase.getInstance().getReference().child("employees");
        mEmployeeDatabaseReference.keepSynced(true);
        mDepartmentDatabaseReference = FirebaseDatabase.getInstance().getReference("departments");
        mDepartmentDatabaseReference.keepSynced(true);

        mDepartmentDatabaseReference.orderByChild("facultyId").equalTo(facultyId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<Department> departments = new ArrayList<>();
                for (DataSnapshot dmo : dataSnapshot.getChildren()){
                    departments.add(dmo.getValue(Department.class));
                }

                mEmployeeDatabaseReference.orderByChild("type").equalTo("staff").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        List<Employee> employees = new ArrayList<>();
                        for (DataSnapshot dmo : dataSnapshot.getChildren()){
                            Employee employee = dmo.getValue(Employee.class);
                            employee.setKey(dmo.getKey());
                            employees.add(employee);
                        }

                        ArrayList<Employee> filteredEmployees = new ArrayList<>();
                        for (int i = 0; i < employees.size(); i++){
                            for (int j = 0; j < departments.size(); j++){
                                if (employees.get(i).getDepartmentId() == departments.get(j).getId()){
                                    Employee employee = employees.get(i);
                                    employee.setDepartment(departments.get(j));
                                    filteredEmployees.add(employee);
                                    break;
                                }
                            }
                        }

                        linearLayoutManager = new LinearLayoutManager(getContext());
                        employeeadapter = new Employeeadapter(filteredEmployees, getContext());
                        mUsersList.setAdapter(employeeadapter);
                        mUsersList.setLayoutManager(linearLayoutManager);

                        employeeadapter.setOnItemClick(new Employeeadapter.OnItemClick() {
                            @Override
                            public void onitemclick(Employee emp, int postion) {

                                Intent profileIntent = new Intent(getContext(), EmployeeProfileActivity.class);
                                profileIntent.putExtra("id", emp.getKey());
                                profileIntent.putExtra("name", emp.getName());
                                profileIntent.putExtra("phone", emp.getPhone());
                                profileIntent.putExtra("department", emp.getDepartment().getName());
                                startActivity(profileIntent);
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
                mProgressDialog.dismiss();
            }
        });

    }


}


