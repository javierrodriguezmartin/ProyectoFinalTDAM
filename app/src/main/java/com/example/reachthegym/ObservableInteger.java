package com.example.reachthegym;

public class ObservableInteger{
    private OnIntegerChangeListener listener;

    private int valor;

    public void setOnIntegerChangeListener(OnIntegerChangeListener listener){
        this.listener = listener;
    }

    public int getValor(){
        return valor;
    }
    public void setValor(int valor){
        this.valor=valor;
        if (listener != null){
            listener.onIntegerChanged(valor);
        }
    }

}
