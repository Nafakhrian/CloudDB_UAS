package id.ub.sch.privateassignment.vokasi024.uas_bsdt;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class BukuAdapter extends ArrayAdapter<Buku> {
    public BukuAdapter(Context context, ArrayList<Buku> bukus){
        super(context, 0, bukus);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.list_buku, parent, false);
        }
        TextView buku_judul = (TextView) convertView.findViewById(R.id.buku_judul);
        TextView buku_pengarang = (TextView) convertView.findViewById(R.id.buku_pengarang);
        TextView buku_penerbit = (TextView) convertView.findViewById(R.id.buku_penerbit);
        TextView buku_stock = (TextView) convertView.findViewById(R.id.buku_stock);

        Buku buku = getItem(position);
            buku_judul.setText("Judul \u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020: "+buku.getJudul());
        buku_pengarang.setText("Pengarang\u0020: "+buku.getPengarang());
         buku_penerbit.setText("Penerbit \u0020\u0020\u0020\u0020\u0020: "+buku.getPenerbit());
            buku_stock.setText("Stok \u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020: "+buku.getStock());

        return convertView;
    }
}
