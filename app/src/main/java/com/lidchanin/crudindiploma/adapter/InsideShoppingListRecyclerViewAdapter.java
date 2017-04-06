package com.lidchanin.crudindiploma.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.data.Product;

import java.util.List;

/**
 * Class <code>InsideShoppingListRecyclerViewAdapter</code> is an adapter for {@link RecyclerView}
 * from {@link com.lidchanin.crudindiploma.activity.InsideShoppingListActivity}.
 *
 * @author Lidchanin
 * @see android.support.v7.widget.RecyclerView.Adapter
 */
public class InsideShoppingListRecyclerViewAdapter
        extends RecyclerView.Adapter<InsideShoppingListRecyclerViewAdapter.InsideShoppingListViewHolder> {

    private List<Product> products;
    private Context context;

    public InsideShoppingListRecyclerViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public InsideShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item_inside_shopping_list, parent, false);
        return new InsideShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InsideShoppingListViewHolder holder, int position) {
        holder.textViewProductName.setText(products.get(position).getName());
        holder.textViewProductCost.setText(String.valueOf(products.get(position).getCost()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    /**
     * Class <code>InsideShoppingListViewHolder</code> is the View Holder for
     * {@link android.support.v7.widget.RecyclerView.Adapter}
     *
     * @see android.support.v7.widget.RecyclerView.ViewHolder
     */
    static class InsideShoppingListViewHolder extends RecyclerView.ViewHolder {

        private CardView cardViewProduct;
        private TextView textViewProductName;
        private TextView textViewProductCost;

        public InsideShoppingListViewHolder(View itemView) {
            super(itemView);
            cardViewProduct
                    = (CardView) itemView.findViewById(R.id.inside_shopping_list_card_view);
            textViewProductName
                    = (TextView) itemView.findViewById(R.id.inside_shopping_list_text_view_name_product_in_card_view);
            textViewProductCost
                    = (TextView) itemView.findViewById(R.id.inside_shopping_list_text_view_cost_product_in_card_view);
        }
    }
}
