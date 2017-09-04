package com.mobile.zenus.cielo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.zenus.cielo.parcellable.Venda;

import org.jetbrains.annotations.NotNull;

import cielo.orders.domain.Credentials;
import cielo.orders.domain.Item;
import cielo.orders.domain.Order;
import cielo.sdk.order.OrderManager;
import cielo.sdk.order.ServiceBindListener;
import cielo.sdk.order.payment.PaymentError;
import cielo.sdk.order.payment.PaymentListener;

public class MainActivity extends AppCompatActivity {


    private OrderManager orderManager;
    private Credentials credentials;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        credentials = new Credentials(getResources().getString(R.string.cliente_id), getResources().getString(R.string.token));



        try {
            orderManager = new OrderManager(credentials, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {

            orderManager.bind(MainActivity.this, serviceBindListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ServiceBindListener serviceBindListener = new ServiceBindListener() {
        @Override
        public void onServiceBound() {
           Toast.makeText(getBaseContext(), "Serviço conectado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceUnbound() {
            Toast.makeText(getBaseContext(), "Serviço desconectado", Toast.LENGTH_SHORT).show();
        }
    };


    public void iniciarCompra(View view){


        Order order = orderManager.createDraftOrder("1");

        String codigo = "1";
        String name = "Coca-cola lata";

// Preço unitário em centavos
        Long unitPrice = 500l;
        int quantity = 4;

// Unidade de medida do produto String
       String unityOfMeasure = "R$";

        order.addItem(codigo, name, unitPrice, quantity, unityOfMeasure);

        order.addItem(codigo, name, unitPrice, quantity, unityOfMeasure);

        orderManager.placeOrder(order);

        pagar(order);
    }

    private void pagar(Order order){

        PaymentListener paymentListener = new PaymentListener() {
            @Override
            public void onStart() {
                Log.d("MinhaApp", "O pagamento começou.");
            }

            @Override
            public void onPayment(@NotNull Order order) {



//                AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(getBaseContext(), R.style.dialogo));
//
//                alert.setTitle("Venda Realizada");

            /*    StringBuilder mensagem = new StringBuilder();

                mensagem.append("Descrição dos produtos: ");

                int i=1;

                Long total = 0L;

                for(Item item: order.getItems()){

                    Long qtdParcial = item.getUnitPrice()*item.getQuantity();
                    mensagem.append("\n "+i+" - "+item.getName()+" \n");
                    mensagem.append("\n Total por Unidade: "+qtdParcial+" "+item.getUnitOfMeasure() +"\n");
                    i++;
                    total+=qtdParcial;
                }

                mensagem.append(" /n Total: "+total);

                mVendas.setText(mensagem.toString());
                */

//                alert.setMessage(mensagem.toString());
//
//                alert.setCancelable(true);
//
//                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                alert.show();

                Intent intent = new Intent(getBaseContext(), ActVendas.class);



                intent.putExtra("vendas", new Venda(order));

                startActivity(intent);


                Log.d("MinhaApp", "Um pagamento foi realizado.");
            }

            @Override public void onCancel() {
                Log.d("MinhaApp", "A operação foi cancelada.");
            }

            @Override public void onError(@NotNull PaymentError paymentError) {
                Log.d("MinhaApp", "Houve um erro no pagamento.");
            }
        };

        String orderId = order.getId();
        long value = order.getPrice();

        orderManager.checkoutOrder(orderId, value, paymentListener);
    }

    public void listarOrden(View view){

        Toast.makeText(getBaseContext(), "Função ainda não implementada", Toast.LENGTH_SHORT).show();
    }

    public void fechar(View view){

        finish();
    }
}
