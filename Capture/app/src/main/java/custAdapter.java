import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.capture.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import static com.example.android.capture.R.id.listimage;

/**
 * Created by Raj on 16/07/2017.
 */

public class custAdapter extends ArrayAdapter{
    Context ctx;
    ArrayList<Uri> t;

    public custAdapter(@NonNull Context context, @LayoutRes int resource,ArrayList<Uri> uris) {
        super(context, R.layout.row, uris );
    }

    public custAdapter(Context context, ArrayList<Uri> uris) {
        super(context, R.layout.row, uris );
        ctx=context;
        t=uris;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View imgrow = inflater.inflate(R.layout.row, parent,false);
        ImageView img = (ImageView) imgrow.findViewById(R.id.listimage);
        Picasso.with(ctx)
                .load(t.get(position))
                .resize(50, 50) // here you resize your image to whatever width and height you like
                .into(img);
        return imgrow;
    }
}
