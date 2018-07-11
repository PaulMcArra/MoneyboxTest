package pgonzalezdesign.com.testmoneybox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountsActivity extends AppCompatActivity {

    private Button isa;
    private Button gia;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_accounts);

        isa = (Button) findViewById(R.id.ISA);
        gia = (Button) findViewById(R.id.GIA);
        textView = (TextView) findViewById(R.id.text);

        isa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isaAccount = new Intent(AccountsActivity.this, IsaActivity.class);
                startActivity(isaAccount);
            }
        });
    }
}