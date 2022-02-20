package ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maru.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import events.DeleteReunionEvent;
import model.Reunion;


public class ReunionListRecyclerViewAdapter extends RecyclerView.Adapter<ReunionListRecyclerViewAdapter.ViewHolder> {

    List<Reunion> mReunion;
    List<Reunion> mReunionAll;

    ReunionListRecyclerViewAdapter(List<Reunion> ReunionList) {
        this.mReunion = ReunionList;
        this.mReunionAll = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_reunion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reunion reunion = mReunion.get(position);
        holder.mReunionColor.setColorFilter(reunion.getColor());
        holder.mReunionInfo.setText(reunion.getInfo());
        holder.mReunionParticipant.setText(reunion.getParticipantsList());

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteReunionEvent(reunion));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mReunion.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_color)
                public ImageView mReunionColor;
        @BindView(R.id.item_list_info)
                public TextView mReunionInfo;
        @BindView(R.id.item_list_delete_button)
                public ImageButton mDeleteButton;
        @BindView(R.id.item_list_participants)
                public TextView mReunionParticipant;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
