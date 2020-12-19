package com.example.scanner_pr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultFragment extends Fragment {


    private String[] Titles = new String[] { "Описание", "Характеристики", "Наличие"};
    private String descr = "";
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_result, container, false);
        TextView art = (TextView) inflatedView.findViewById(R.id.code_artikul);
        TextView pri = (TextView) inflatedView.findViewById(R.id.price);
        MainActivity ma = (MainActivity)this.getActivity();//передача res(номер штрих-кода) из mainActivity
        String str=ma.getRes();
      // pri.setText("test"+getString(R.string.add_to_price));
       // art.setText(str);



        Map<String, String> map;// коллекция для групп
        ArrayList<Map<String, String>> groupDataList = new ArrayList<>();// заполняем коллекцию групп из массива с названиями групп

        for (String group : Titles) {// заполняем список атрибутов для каждой группы
            map = new HashMap<>();
            map.put("groupName", group); // заголовки
            groupDataList.add(map);
        }

        // список атрибутов групп для чтения
        String groupFrom[] = new String[] { "groupName" };
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[] { android.R.id.text1 };

        // создаем общую коллекцию для коллекций элементов
        ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();

        // в итоге получится сhildDataList = ArrayList<сhildDataItemList>


        // создаем коллекцию элементов для первой группы
        ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
        // заполняем список атрибутов для каждого элемента
        //for (String descr : mWinterMonthsArray) {
            map = new HashMap<>();
            map.put("text", descr); // название месяца
            сhildDataItemList.add(map);
        //}
        // добавляем в коллекцию коллекций
        сhildDataList.add(сhildDataItemList);



        // создаем коллекцию элементов для второй группы
        сhildDataItemList = new ArrayList<>();
            map = new HashMap<>();
            map.put("text", descr);
            сhildDataItemList.add(map);

        сhildDataList.add(сhildDataItemList);

        // создаем коллекцию элементов для третьей группы
        сhildDataItemList = new ArrayList<>();
            map = new HashMap<>();
            map.put("text", descr);
            сhildDataItemList.add(map);
        сhildDataList.add(сhildDataItemList);

        // создаем коллекцию элементов для четвертой группы
        сhildDataItemList = new ArrayList<>();

            map = new HashMap<>();
            map.put("text", descr);
            сhildDataItemList.add(map);
        сhildDataList.add(сhildDataItemList);




        // список атрибутов элементов для чтения
        String childFrom[] = new String[] { "text" };
        // список ID view-элементов, в которые будет помещены атрибуты
        // элементов
        int childTo[] = new int[] { android.R.id.text1 };

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                getContext(), groupDataList, android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);
        ExpandableListView expandableListView = (ExpandableListView) inflatedView.findViewById(R.id.expListView);
        expandableListView.setAdapter(adapter);


        return inflatedView;
    }

}
