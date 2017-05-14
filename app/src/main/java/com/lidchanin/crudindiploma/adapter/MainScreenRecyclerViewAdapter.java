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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.activity.InsideShoppingListActivity;
import com.lidchanin.crudindiploma.activity.MainScreenPopUpWindowActivity;
import com.lidchanin.crudindiploma.data.dao.ShoppingListDAO;
import com.lidchanin.crudindiploma.data.model.ShoppingList;

import java.util.List;

/**
 * Class <code>MainScreenRecyclerViewAdapter</code> is an adapter for {@link RecyclerView} from
 * {@link com.lidchanin.crudindiploma.activity.MainScreenActivity}. This class extends
 * {@link android.support.v7.widget.RecyclerView.Adapter}.
 *
 * @author Lidchanin
 * @see android.support.v7.widget.RecyclerView.Adapter
 */
public class MainScreenRecyclerViewAdapter
        extends RecyclerView.Adapter<MainScreenRecyclerViewAdapter.MainScreenViewHolder> {

    private Context context;
    private List<ShoppingList> shoppingLists;

    public MainScreenRecyclerViewAdapter(List<ShoppingList> shoppingLists, Context context) {
        this.shoppingLists = shoppingLists;
        this.context = context;
    }

    @Override
    public MainScreenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item_shopping_list, parent, false);
        return new MainScreenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainScreenViewHolder holder, final int position) {
        holder.textViewShoppingListName.setText(shoppingLists.get(position).getName());
        holder.cardViewShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InsideShoppingListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("shoppingListId",
                        shoppingLists.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
        holder.cardViewShoppingList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, MainScreenPopUpWindowActivity.class);
                intent.putExtra("shoppingListId",
                        shoppingLists.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
                return true;
            }
        });
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndShowAlertDialogForDelete(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    /**
     * Method <code>createAndShowAlertDialogForDelete</code> create and shows a dialog, which need
     * to confirm deleting shopping list.
     *
     * @param adapterPosition is the position, where record about shopping list are located.
     */
    private void createAndShowAlertDialogForDelete(final int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.delete_shopping_list);
        builder.setMessage(R.string.you_are_sure_you_want_to_delete_this_shopping_list);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShoppingListDAO shoppingListDAO = new ShoppingListDAO(context);
                shoppingListDAO.delete(shoppingLists.get(adapterPosition));
                shoppingLists.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition, shoppingLists.size());
                dialog.dismiss();
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
     * Class <code>MainScreenViewHolder</code> is the View Holder for
     * {@link MainScreenRecyclerViewAdapter}.
     *
     * @see android.support.v7.widget.RecyclerView.ViewHolder
     */
    static class MainScreenViewHolder extends RecyclerView.ViewHolder {

        private CardView cardViewShoppingList;
        private TextView textViewShoppingListName;
        private ImageButton imageButtonDelete;

        MainScreenViewHolder(View itemView) {
            super(itemView);
            cardViewShoppingList = (CardView)
                    itemView.findViewById(R.id.main_screen_card_view_shopping_list);
            textViewShoppingListName = (TextView)
                    itemView.findViewById(R.id.main_screen_text_view_name_shopping_list_in_card_view);
            imageButtonDelete = (ImageButton)
                    itemView.findViewById(R.id.main_screen_image_button_delete_in_card_view);
        }
    }
}
