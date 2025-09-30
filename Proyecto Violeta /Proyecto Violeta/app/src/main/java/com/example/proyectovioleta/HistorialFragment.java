package com.example.proyectovioleta;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectovioleta.DBHelper;
import com.example.proyectovioleta.R;
import com.example.proyectovioleta.EventoAdapter;
import com.example.proyectovioleta.Evento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorialFragment extends Fragment {

    private RecyclerView recyclerEventos;
    private EventoAdapter eventoAdapter;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        recyclerEventos = view.findViewById(R.id.recyclerEventos);
        recyclerEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(requireContext());

        cargarEventos();

        return view;
    }

    private void cargarEventos() {
        List<Evento> listaEventos = new ArrayList<>();
        Cursor cursor = dbHelper.obtenerEventos();

        if (cursor.moveToFirst()) {
            do {
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
                long fechaMillis = cursor.getLong(cursor.getColumnIndexOrThrow("fecha"));
                String fotoRuta = cursor.getString(cursor.getColumnIndexOrThrow("fotoRuta"));

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String fechaFormateada = sdf.format(new Date(fechaMillis));

                listaEventos.add(new Evento(tipo, fechaFormateada, fotoRuta));
            } while (cursor.moveToNext());
        }
        cursor.close();

        eventoAdapter = new EventoAdapter(listaEventos);
        recyclerEventos.setAdapter(eventoAdapter);
    }
}
