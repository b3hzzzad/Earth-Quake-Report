package th.o.earthquakereport;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    private List<Earthquake> earthquakes;

    public void setEarthquakes(List<Earthquake> earthquakes) {
        if (earthquakes != null) {
            // Filter out null strings
            List<Earthquake> filteredEarthquakes = new ArrayList<>();
            for (Earthquake earthquake : earthquakes) {
                if (earthquake != null && earthquake.getProperties() != null && earthquake.getProperties().getPlace() != null) {
                    filteredEarthquakes.add(earthquake);
                }
            }
            this.earthquakes = filteredEarthquakes;
        } else {
            this.earthquakes = null;
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recycleritems, parent, false);
        return new EarthquakeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        Earthquake earthquake = earthquakes.get(position);
        holder.bind(earthquake);
    }

    @Override
    public int getItemCount() {
        return earthquakes != null ? earthquakes.size() : 0;
    }

    public static class EarthquakeViewHolder extends RecyclerView.ViewHolder {

        private TextView magnitudeTextView;
        private TextView textViewPlace, textViewOffSet;
        private TextView timeTextView, dateTextView;
        GradientDrawable gradientDrawable;

        public EarthquakeViewHolder(@NonNull View itemView) {
            super(itemView);
            magnitudeTextView = itemView.findViewById(R.id.mag_text_view);
            textViewPlace = itemView.findViewById(R.id.place_text_view);
            textViewOffSet = itemView.findViewById(R.id.offset_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            timeTextView = itemView.findViewById(R.id.time_text_view);

            // Initialize the gradientDrawable object
            gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.OVAL);

        }

        public void bind(Earthquake earthquake) {
            double magnitude = earthquake.getProperties().getMag();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String magnitudeFormatted = decimalFormat.format(magnitude);

            magnitudeTextView.setText(magnitudeFormatted);
            gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(itemView.getContext(), R.drawable.magnitude_circle);
            magnitudeTextView.setBackground(gradientDrawable);

            int magnitudeForSwitch = (int) earthquake.getProperties().getMag();

            switch (magnitudeForSwitch) {
                case 1:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude1));
                    break;
                case 2:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude2));
                    break;
                case 3:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude3));
                    break;
                case 4:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude4));
                    break;
                case 5:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude5));
                    break;
                case 6:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude6));
                    break;
                case 7:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude7));
                    break;
                case 8:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude8));
                    break;
                case 9:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude9));
                    break;
                case 10:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.magnitude10plus));
                    break;

                default:
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), com.google.android.material.R.color.design_default_color_background));
                    break;
            }


            String[] fullLocation = split(earthquake.getProperties().getPlace());

            textViewPlace.setText(fullLocation[1]);

            textViewOffSet.setText(fullLocation[0] + " of");

            // Convert the time in milliseconds to hour and minute format
            long timeMillis = earthquake.getProperties().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = sdf.format(new Date(timeMillis));
            dateTextView.setText(date);

            SimpleDateFormat stf = new SimpleDateFormat("HH-mm", Locale.getDefault());
            String time = stf.format(new Date(timeMillis));
            timeTextView.setText(time);

        }

        private String[] split(String string) {
            String[] array = {};
            if (string != null && string.contains("of")) {
                array = string.split("of");
            } else {
                array = new String[]{"Near", string};
            }
            return array;
        }

    }
}
