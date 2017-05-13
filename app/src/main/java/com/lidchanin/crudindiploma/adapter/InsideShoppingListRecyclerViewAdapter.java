package com.lidchanin.crudindiploma.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.activity.InsideShoppingListActivity;
import com.lidchanin.crudindiploma.activity.InsideShoppingListUpdateProductPopUpWindowActivity;
import com.lidchanin.crudindiploma.data.dao.ProductDAO;
import com.lidchanin.crudindiploma.data.model.Product;

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
    private long shoppingListId;
    private Context context;

    public InsideShoppingListRecyclerViewAdapter(List<Product> products, Context context,
                                                 long shoppingListId) {
        this.products = products;
        this.context = context;
        this.shoppingListId = shoppingListId;
    }

    @Override
    public InsideShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item_inside_shopping_list, parent, false);
        return new InsideShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InsideShoppingListViewHolder holder, final int position) {
        holder.textViewProductName.setText(products.get(position).getName());
        holder.textViewProductCost.setText(String.valueOf(products.get(position).getCost()));
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndShowAlertDialog(holder.getAdapterPosition());
            }
        });
        holder.cardViewProduct.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context,
                        InsideShoppingListUpdateProductPopUpWindowActivity.class);
                intent.putExtra("shoppingListId", shoppingListId);
                intent.putExtra("productId", products.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Method <code>createAndShowAlertDialog</code> create and shows a dialog, which need to
     * confirm deleting product.
     *
     * @param adapterPosition is the position, where record about product are located.
     */
    private void createAndShowAlertDialog(final int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.delete_product);
        builder.setMessage(R.string.you_are_sure_you_want_to_delete_this_product);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProductDAO productDAO = new ProductDAO(context);
                productDAO.delete(shoppingListId, products.get(adapterPosition).getId());
                // FIXME: 12.04.2017 Doing something with this shit code!
                Intent intent = new Intent(context, InsideShoppingListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("shoppingListId", shoppingListId);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
        private ImageButton imageButtonDelete;

        InsideShoppingListViewHolder(View itemView) {
            super(itemView);
            cardViewProduct = (CardView)
                    itemView.findViewById(R.id.inside_shopping_list_card_view);
            textViewProductName = (TextView) itemView.
                    findViewById(R.id.inside_shopping_list_text_view_product_name_in_card_view);
            textViewProductCost = (TextView) itemView.
                    findViewById(R.id.inside_shopping_list_text_view_product_cost_in_card_view);
            imageButtonDelete = (ImageButton) itemView
                    .findViewById(R.id.inside_shopping_list_image_button_delete_in_card_view);
        }
    }
}
