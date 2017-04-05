package com.lidchanin.crudindiploma.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.data.ShoppingList;

import java.util.List;

/**
 * Class <code>MainScreenRecyclerViewAdapter</code> is an adapter for {@link RecyclerView} from
 * {@link com.lidchanin.crudindiploma.activity.MainScreenActivity}.
 *
 * @author Lidchanin
 * @see android.support.v7.widget.RecyclerView.Adapter
 */
public class MainScreenRecyclerViewAdapter
        extends RecyclerView.Adapter<MainScreenRecyclerViewAdapter.ShoppingListViewHolder> {

    private Context context;
    private List<ShoppingList> shoppingLists;

    public MainScreenRecyclerViewAdapter(List<ShoppingList> shoppingLists, Context context) {
        this.shoppingLists = shoppingLists;
        this.context = context;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item_shopping_list, parent, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShoppingListViewHolder holder, int position) {
        holder.textViewNameShoppingList.setText(shoppingLists.get(position).getName());
        // FIXME: 05.04.2017 Inside shopping list
        holder.cardViewShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InsideShoppingList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("shoppingListId",
                        shoppingLists.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    static class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        private CardView cardViewShoppingList;
        private TextView textViewNameShoppingList;

        ShoppingListViewHolder(View itemView) {
            super(itemView);
            cardViewShoppingList
                    = (CardView) itemView.findViewById(R.id.main_screen_card_view_shopping_list);
            textViewNameShoppingList
                    = (TextView) itemView.findViewById(R.id.main_screen_text_view_name_shopping_list_in_card_view);
        }
    }
}
