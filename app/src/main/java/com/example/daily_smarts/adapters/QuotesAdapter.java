package com.example.daily_smarts.adapters;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.daily_smarts.models.local.QuoteEntity;
import com.example.daily_smarts.R;
import com.example.daily_smarts.databinding.QuoteItemBinding;
import com.example.daily_smarts.models.local.QuoteViewModel;
import com.example.daily_smarts.models.local.QuotesDao;
import com.example.daily_smarts.models.local.QuotesRepository;

import static android.content.ContentValues.TAG;

public class QuotesAdapter extends ListAdapter<QuoteEntity, QuotesViewHolder> {

    QuotesViewHolder.QuotesListener listener;
    QuotesDao quotesDao;

    public QuotesAdapter(QuotesViewHolder.QuotesListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<QuoteEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<QuoteEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull QuoteEntity oldItem, @NonNull QuoteEntity newItem) {
            if(oldItem != null && newItem != null){
                return oldItem.getUid() == newItem.getUid();
            }
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull QuoteEntity oldItem, @NonNull QuoteEntity newItem) {
            if(oldItem != null && newItem != null){
                return oldItem.getQuoteText().equals(newItem.getQuoteText()) &&
                        oldItem.getQuoteAuthor().equals(newItem.getQuoteAuthor());
            }
            return false;
        }
    };

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuoteItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.quote_item, parent, false);
        QuotesViewHolder quotesViewHolder = new QuotesViewHolder(binding, listener);
        return quotesViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {
        QuoteEntity currentQuote = getItem(position);
        if(currentQuote != null){
            Log.e(TAG, currentQuote.toString());
            holder.binding.quoteTextView.setText(currentQuote.getQuoteText());
            holder.binding.authorTextView.setText(currentQuote.getQuoteAuthor());
            holder.binding.btnLike.setBackgroundResource(R.drawable.ic_favorite_black_24px);
            holder.setQuoteEntity(currentQuote);
        }
    }


}
