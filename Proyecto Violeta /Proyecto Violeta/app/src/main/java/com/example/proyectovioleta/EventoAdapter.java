package com.example.proyectovioleta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectovioleta.R;
import com.example.proyectovioleta.Evento;

import java.io.File;
import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private List<Evento> eventos;

    public EventoAdapter(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.txtTipo.setText(evento.getTipo());
        holder.txtFecha.setText(evento.getFecha());

        if (evento.getFotoRuta() != null && !evento.getFotoRuta().isEmpty()) {
            File imgFile = new File(evento.getFotoRuta());
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imgEvento.setImageBitmap(bitmap);
                holder.imgEvento.setVisibility(View.VISIBLE);
            } else {
                holder.imgEvento.setVisibility(View.GONE);
            }
        } else {
            holder.imgEvento.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView txtTipo, txtFecha;
        ImageView imgEvento;

        EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTipo = itemView.findViewById(R.id.txtTipoEvento);
            txtFecha = itemView.findViewById(R.id.txtFechaEvento);
            imgEvento = itemView.findViewById(R.id.imgEvento);
        }
    }
}
