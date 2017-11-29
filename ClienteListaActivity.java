package academia.com.br.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import academia.com.br.adapter.ClienteAdapter;
import academia.com.br.daos.ClienteDao;
import academia.com.br.models.Cliente;

public class ClienteListaActivity extends AppCompatActivity {

    private ListView lvClientes;
    private Button btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_lista);
    }
    protected void onResume() {
        super.onResume();
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        lvClientes = (ListView) findViewById(R.id.lista_clientes);
        btSalvar = (Button) findViewById(R.id.salvar);
        carregarLista();
        registerForContextMenu(lvClientes);
    }

    private void carregarLista() {
        List<Cliente> clientes = new ClienteDao(this).buscarTodos();
        ClienteAdapter adapter = new ClienteAdapter(this, clientes);
        lvClientes.setAdapter(adapter);
    }

    private void definirEventos() {
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChamaFormulario = new Intent(ClienteListaActivity.this, ClienteFormularioActivity.class);
                startActivity(intentChamaFormulario);
            }
        });

        lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id) {
                Cliente cliente = (Cliente) lvClientes.getItemAtPosition(posicao);
                Intent intentChamaFormulario = new Intent(ClienteListaActivity.this, ClienteFormularioActivity.class);
                intentChamaFormulario.putExtra("cliente", cliente);
                startActivity(intentChamaFormulario);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_opcoes, menu);
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo clienteEscolhido;
        clienteEscolhido = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Cliente cliente = (Cliente) lvClientes.getItemAtPosition(clienteEscolhido.position);
        switch (item.getItemId()) {
            case R.id.item_ligar:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 9);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + cliente.getTelefone()));
                    item.setIntent(intentLigar);
                }
                break;
            case R.id.item_deletar:
                ClienteDao dao = new ClienteDao(this);
                dao.deletar(cliente);
                carregarLista();
                break;
                }

        return super.onContextItemSelected(item);
    }
}




























