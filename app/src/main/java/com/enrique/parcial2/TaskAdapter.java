package com.enrique.parcial2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    private final int EDIT_TASK_REQUEST_CODE = 2;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.date.setText(task.getDate().toString()); // Considera formatear la fecha de una manera más amigable para el usuario
        holder.time.setText(task.getTime());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description, date, time;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            description = itemView.findViewById(R.id.taskDescription);
            date = itemView.findViewById(R.id.taskDate);
            time = itemView.findViewById(R.id.taskTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Task task = taskList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View view = inflater.inflate(R.layout.dialog_edit_task, null);  // Aquí necesitarás un layout para editar la tarea

            EditText editTitle = view.findViewById(R.id.editTitle);
            EditText editDescription = view.findViewById(R.id.editDescription);
            EditText editDate = view.findViewById(R.id.editDate);
            EditText editTime = view.findViewById(R.id.editTime);

            editTitle.setText(task.getTitle());
            editDescription.setText(task.getDescription());
            editDate.setText(task.getDate());
            editTime.setText(task.getTime());

            builder.setView(view)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Aquí actualizamos el objeto Task y notificamos que el dataset ha cambiado
                            task.setTitle(editTitle.getText().toString());
                            task.setDescription(editDescription.getText().toString());
                            task.setDate(editDate.getText().toString());
                            task.setTime(editTime.getText().toString());

                            notifyItemChanged(position);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Usuario canceló la edición, no necesitas hacer nada aquí
                        }
                    });

            builder.create().show();
        }
    }
}
