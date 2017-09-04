package com.mobile.zenus.cielo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mobile.zenus.cielo.parcellable.Venda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cielo.orders.domain.Item;

public class ActVendas extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Venda vendaAtual;

    private TextView mTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_vendas_historico);

        Intent intent = getIntent();

        vendaAtual = intent.getParcelableExtra("vendas");

        mTotal = (TextView) findViewById(R.id.txtTotal);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new com.mobile.zenus.cielo.adapter.ExpandableListAdapter(getBaseContext(), listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);
    }

    private void prepareListData(){

        listDataHeader = new ArrayList<String>();

        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 25 Vendas");
        listDataHeader.add("Vendas de Hoje");
        listDataHeader.add("Vendas de Ontem");


        // Adding child data
        List<String> top25 = new ArrayList<String>();

        for (int i=1; i<=25; i++){

            top25.add("Venda "+i);
        }




        List<String> listaVendasHoje = new ArrayList<String>();

        int i=0;
        Long total = 0L;

        for(Item item: vendaAtual.getOrdem().getItems()){

            listaVendasHoje.add(i+" - "+item.getName() +" x Qtd "+item.getQuantity());
            i++;
            total += item.getUnitPrice()*item.getQuantity();

        }

        mTotal.setText("Total de Vendas: R$ "+total/100 );


        List<String> listaVendasOntem = new ArrayList<String>();

        for (int j=1; j<=25; j++){

            listaVendasOntem.add(j+ " Venda");
        }


        listDataChild.put(listDataHeader.get(0), top25); // Header, Child data
        listDataChild.put(listDataHeader.get(1), listaVendasHoje);
        listDataChild.put(listDataHeader.get(2), listaVendasOntem);

    }
}
