package com.br.projekt.internshipproject;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;


        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<UserModel> userModels;
    private static LayoutInflater inflator = null;
    UserModel tempValues = null;

    // CustomAdapter constructor
    public CustomAdapter(Activity a, ArrayList<UserModel> d) {
        activity = a;
        userModels = d;
        inflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    public void updateUsers(ArrayList<UserModel> users){
        this.userModels = users;
    }

    @Override
    public int getCount() {
        if (userModels.size() <= 0)
            return 1;
        return userModels.size();

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*** create a holder class to hold the inflated xml file elements **/
    public static class ViewHolder {
        public TextView text;
        public ImageView image;
    }


    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        LayoutInflater inflater1 = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater1.inflate(R.layout.avatarlayout, parent, false);

        if (convertView == null) {
            vi = inflator.inflate(R.layout.avatarlayout, null);
            holder = new ViewHolder();
            holder.text = vi.findViewById(R.id.textView);
            holder.image = vi.findViewById(R.id.avatar);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        if (userModels.size() <= 0) {
            holder.text.setText("NO DATA");
        } else {
            tempValues = null;
            tempValues = userModels.get(position);
            holder.text.setText(tempValues.getFirstName() +" "+ tempValues.getLastName());
            Picasso.get().load(tempValues.getAvatar()).into(holder.image);
        }
        return vi;
    }

}