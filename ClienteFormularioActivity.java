package academia.com.br.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import academia.com.br.daos.ClienteDao;
import academia.com.br.models.Cliente;

public class ClienteFormularioActivity extends AppCompatActivity{

    private EditText etNome, etTelefone, etPeso, etAltura;
    private Button btSalvar;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_formulario);
        inicializarComponentes();
        definirEventos();
    }

    private void definirEventos() {
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { salvar(); }
        });
    }

    private void inicializarComponentes() {
        etNome = (EditText) findViewById(R.id.formulario_nome);
        etTelefone = (EditText) findViewById(R.id.formulario_telefone);
        etPeso = (EditText) findViewById(R.id.formulario_peso);
        etAltura = (EditText) findViewById(R.id.formulario_altura);
        btSalvar = (Button) findViewById(R.id.formulario_salvar);

        Intent intent = getIntent();
        cliente = (Cliente) intent.getSerializableExtra("cliente");
        if (cliente != null) {
            preencherCampos(cliente);
        } else {
            cliente = new Cliente();
        }
    }

    private void salvar() {
        ClienteDao dao = new ClienteDao(this);

        cliente.setNome(etNome.getText().toString());
        cliente.setTelefone(etTelefone.getText().toString());
        cliente.setPeso(etPeso.getText().toString());
        cliente.setAltura(etAltura.getText().toString());

        if (cliente.getId() == null) {
            dao.inserir(cliente);
        } else {
            dao.editar(cliente);
        }
        finish();
    }

    private void limparCampos() {
        etNome.setText("");
        etTelefone.setText("");
        etPeso.setText("");
        etAltura.setText("");
    }

    private void preencherCampos(Cliente cliente) {
        etNome.setText(cliente.getNome());
        etTelefone.setText(cliente.getTelefone());
        etPeso.setText(cliente.getPeso());
        etAltura.setText(cliente.getAltura());

    }
}

















