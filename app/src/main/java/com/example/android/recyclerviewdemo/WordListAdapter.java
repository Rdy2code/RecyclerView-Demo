package com.example.android.recyclerviewdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    //Variable to hold the word list that will be used by the adapter
    private final LinkedList<String> mWordList;

    //Adapter variables
    private LayoutInflater mInflater;

    //Listener Variable
    private final ListItemClickListener mOnClickListener;

    //Interface to implement clicklistener from MainActivity
    public interface ListItemClickListener {
        void onListItemClick (int clickedItemIndex);
    }

    public WordListAdapter(Context context, LinkedList<String> wordList, ListItemClickListener clickListener) {
        this.mWordList = wordList;
        this.mInflater = LayoutInflater.from(context);
        this.mOnClickListener = clickListener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the list item layout
        View listItemView = mInflater.inflate(R.layout.list_item, parent, false);
        //Attach the inflated view to the ViewHolder
        WordViewHolder viewHolder = new WordViewHolder(listItemView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String mCurrentWord = mWordList.get(position);
        holder.wordItemView.setText(mCurrentWord);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    /**
     * Class holds the views that will be populated with data and registers each itemView with a
     * click handler
     */
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Add variables for each of the Views inside the list item layout
        public final TextView wordItemView;
        //Declare the adapter that will be used with the ViewHolder
        public final WordListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            //Set the adapter
            this.mAdapter = adapter;
            //Connect the listener to the view
            itemView.setOnClickListener(this);
        }

        //Change the word when the item is clicked
        @Override
        public void onClick(View view) {
            //Get the position of the list item in the list
            int position = getLayoutPosition();
            //Get a reference to the word for that item from data source
            String word = mWordList.get(position);
            //Change the word in the list
            mWordList.set(position, "Clicked! " + word);
            //Notify the adapter the data has changed
            //Could call this from main activity
            mAdapter.notifyDataSetChanged();
            //Pass the position to the Interface, for use by the MainActivity
            mOnClickListener.onListItemClick(position);
        }
    }
}
