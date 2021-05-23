package com.example.convector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

    private final List<Currency> currencyList;
    private final Context context;
    private final Currency currency;
    private final int button;
    private final MainInterface.Controller controller = new Controller(null);


    public ChooseAdapter(List<Currency> currencyList, Context context, int numberButton) {
        this.currencyList = currencyList;
        this.context = context;
        this.currency = numberButton == 1 ? controller.getFirstCurrency() : controller.getSecondCurrency();
        this.button = numberButton;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView currencyName;
        TextView currencyCode;
        ImageView checkmark;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.currencyCode = itemView.findViewById(R.id.currencyCode);
            this.currencyName = itemView.findViewById(R.id.currencyName);
            this.checkmark = itemView.findViewById(R.id.checkmark);
            this.constraintLayout = itemView.findViewById(R.id.item);
        }
    }

    @NonNull
    @Override
    public ChooseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseAdapter.ViewHolder holder, int position) {
        if (currency == currencyList.get(position)) {
            holder.checkmark.setVisibility(View.VISIBLE);
        } else {
            holder.checkmark.setVisibility(View.INVISIBLE);
        }
        holder.currencyName.setText(currencyList.get(position).getName());
        holder.currencyCode.setText(currencyList.get(position).getCharCode());
        holder.constraintLayout.setOnClickListener(view -> {
            MainInterface.NavigationFragments listener = (MainInterface.NavigationFragments) context;
            if (button == 1) {
                controller.setFirstCurrency(currencyList.get(position));
            } else {
                controller.setSecondCurrency(currencyList.get(position));
            }
            listener.goToCalculate();
        });
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }


}
