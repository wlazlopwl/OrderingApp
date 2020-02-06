package com.example.pawel.myapp.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawel.myapp.Adapter.WorkerDialogListAdapter;
import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.UserModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SettingsChangeMyEmail;
import com.example.pawel.myapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pawel.myapp.Admin.AdminUserList.allUserListAdapter;


public class SimpleUserBottomOption extends BottomSheetDialogFragment {
    SettingsChangeMyEmail settingsChangeMyEmail;
    Context mContext;
    Integer position;
    public ArrayList<UserModel> WorkerList;
    RecyclerView recyclerView;
    public static  AlertDialog selectWorker;
    public com.example.pawel.myapp.Adapter.WorkerDialogListAdapter workerDialogListAdapter;
    String idWorker;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_bottom_sheet_user_list, container, false);
        mContext = getContext();
        final LinearLayout changeEmail = (LinearLayout) view.findViewById(R.id.user_list_bottom_sheet_change_email);
        LinearLayout changePass = (LinearLayout) view.findViewById(R.id.user_list_bottom_sheet_change_pass);
        LinearLayout changeWorkerForUser = (LinearLayout) view.findViewById(R.id.user_list_bottom_sheet_change_worker);
        LinearLayout deleteUser = (LinearLayout) view.findViewById(R.id.user_list_bottom_sheet_delete_user);

        TextView mFullName = (TextView) view.findViewById(R.id.user_list_bottom_sheet_name);
        final TextView mChangeEmailTV = (TextView) view.findViewById(R.id.user_list_bottom_sheet_change_email_TV);
        final TextView mChangePassTV = (TextView) view.findViewById(R.id.user_list_bottom_sheet_change_pass_TV);
        final TextView mDeleteUserTV = (TextView) view.findViewById(R.id.user_list_bottom_sheet_delete_user_TV);
        final TextView mSelectWorkerTV = (TextView) view.findViewById(R.id.user_list_bottom_sheet_select_worker_TV);


        settingsChangeMyEmail = new SettingsChangeMyEmail();

        final Bundle bundle = this.getArguments();
        final String userId = bundle.getString("userId");
        position = bundle.getInt("position");
        final String fullName = bundle.getString("name") + " " + bundle.getString("surname");
        mFullName.setText(fullName);
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(mChangeEmailTV.getText().toString());
                dialog.setMessage("Wprowadź nowy email dla użytkownika: " + fullName);

                dialog.setIcon(R.drawable.user);
                final EditText input = new EditText(getContext());
                input.setHint("Nowy e-mail");


                dialog.setPositiveButton("Zmień", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newEmail = input.getText().toString().trim();
                        if (!(newEmail.isEmpty())) {
                            updateEmail(userId, newEmail);
                        } else {
                            Toast.makeText(getContext(), "Wpisz e-mail", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setView(input);

                AlertDialog changeDialog = dialog.create();
                changeDialog.show();

            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(mChangePassTV.getText().toString());
                dialog.setMessage("Wprowadź nowe hasło dla użytkownika: " + fullName);

                dialog.setIcon(R.drawable.padlock);
                final EditText input = new EditText(getContext());
                input.setHint("Nowe hasło");


                dialog.setPositiveButton("Zmień", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPass = input.getText().toString().trim();
                        if (!(newPass.isEmpty())) {
                            updatePassword(userId, newPass);
                        } else {

                            Toast.makeText(getContext(), "Nie wprowadzono nowego hasła", Toast.LENGTH_SHORT).show();

                        }


                    }
                }).setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setView(input);

                AlertDialog changeDialog = dialog.create();
                changeDialog.show();

            }
        });

        

        changeWorkerForUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView =  inflater.inflate(R.layout.layout_select_worker_dialog, null);
                dialog.setView(dialogView);
                getWorker();
                recyclerView =  dialogView.findViewById(R.id.select_worker_dialog_RV);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                dialog.setTitle(mSelectWorkerTV.getText().toString() + " dla " + fullName);
                dialog.setIcon(R.drawable.user);
                dialog.setPositiveButton("Zmień", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idWorker = WorkerDialogListAdapter.idWorker;
                        changeWorker(userId, idWorker);
                    }
                }).setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                selectWorker = dialog.create();
                selectWorker.show();
                Button button;
                button = selectWorker.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setEnabled(false);


            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(mDeleteUserTV.getText().toString());
                dialog.setMessage("Czy na pewno chcesz usunąć użytkownika: " + fullName + " ?");

                dialog.setIcon(R.drawable.ic_delete_black_24dp);


                dialog.setPositiveButton("Tak, chcę usunąć", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(userId);
                        getDialog().dismiss();

                    }
                }).setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog changeDialog = dialog.create();
                changeDialog.show();
            }
        });
        return view;
    }



    private void changeWorker(final String userId, final String newEmail) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_CHANGE_WORKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("id", userId);
                params.put("workerId", idWorker);


                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }


    private void updateEmail(final String userId, final String newEmail) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_UPDATE_EMAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("id", userId);
                params.put("email", newEmail);


                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    private void updatePassword(final String idUser, final String firstNewPass) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_UPDATE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
                ;

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("id", idUser);
                params.put("password", firstNewPass);


                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    private void deleteUser(final String idUser) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_DELETE_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Usunięto")) {
                    AdminUserList.AllUserList.remove(position);
                    allUserListAdapter.notifyItemRemoved(position);

                }
                else{
                    Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
                }






            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("id", idUser);


                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    public void getWorker() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GET_WORKER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    WorkerList = new ArrayList();


                    JSONArray jsonArray = new JSONArray(response);

                    Log.d("aa", response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        UserModel user = new UserModel();
                        JSONObject object = jsonArray.getJSONObject(i);
                        user.setName(object.getString("name").trim());
                        user.setSurname(object.getString("surname").trim());
                        user.setId(object.getString("id").trim());


                        WorkerList.add(user);


                    }
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setClickable(true);
                    workerDialogListAdapter = new WorkerDialogListAdapter(WorkerList);
                    workerDialogListAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(workerDialogListAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);


    }
}
