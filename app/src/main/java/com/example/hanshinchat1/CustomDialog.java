package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanshinchat1.recycler.VerticalDecoration;

import java.util.Map;

public class CustomDialog extends Dialog {
    private Context context;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    public static AlertDialog dialog;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        builder = new AlertDialog.Builder(context);
        inflater = LayoutInflater.from(context);
    }

    public Dialog priorityDialog(int priority, Map<String, String> idealMap) {
        View view = inflater.inflate(R.layout.ideal_prioity_dialog, null);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_idealPriority);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalDecoration(20));
        recyclerView.setAdapter(new RecyclerIdealAdapter(context, priority, idealMap));

        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        return dialog;

    }
}
