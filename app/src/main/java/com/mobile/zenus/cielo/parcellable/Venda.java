package com.mobile.zenus.cielo.parcellable;

import android.os.Parcel;
import android.os.Parcelable;

import cielo.orders.domain.Order;

/**
 * Created by Tuca on 04/09/2017.
 */
public class Venda implements Parcelable {


    private Order ordem;

    public Order getOrdem(){

        return this.ordem;
    }

    protected Venda(Parcel in) {
        ordem = (Order) in.readValue(Order.class.getClassLoader());
    }

    public Venda(Order order){
        this.ordem = order;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ordem);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Venda> CREATOR = new Parcelable.Creator<Venda>() {
        @Override
        public Venda createFromParcel(Parcel in) {
            return new Venda(in);
        }

        @Override
        public Venda[] newArray(int size) {
            return new Venda[size];
        }
    };
}
