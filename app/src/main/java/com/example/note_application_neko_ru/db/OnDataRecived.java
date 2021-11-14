package com.example.note_application_neko_ru.db;

import com.example.note_application_neko_ru.adapter.RcvItemList;

import java.util.List;

public interface OnDataRecived {
    void OnReceivedInterface(List<RcvItemList> list);
}
