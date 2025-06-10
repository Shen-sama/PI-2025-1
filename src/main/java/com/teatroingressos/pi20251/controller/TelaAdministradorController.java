package com.teatroingressos.pi20251.controller;

import com.teatroingressos.pi20251.app.MainApp;
import com.teatroingressos.pi20251.util.SceneSwap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TelaAdministradorController {

    @FXML
    private Button btnCadastrarPeca;

    @FXML
    private Button btnCadastrarSessao;

    @FXML
    private Button btnDesabilitarSessao;

    @FXML
    private Button btnHabilitarSessao;

    @FXML
    private ComboBox<String> cbDuracaoHora;

    @FXML
    private ComboBox<String> cbDuracaoMin;

    @FXML
    private ComboBox<String> cbHoraSessao;

    @FXML
    private ComboBox<String> cbHorarioPeca;

    @FXML
    private ComboBox<String> cbMinSessao;

    @FXML
    private ComboBox<String> cbNomePeca;

    @FXML
    private ComboBox<String> cbNomePecaRemover;

    @FXML
    private ToggleGroup estatistica;

    @FXML
    private LineChart<String, Number> graficoEstatistica;

    @FXML
    private AnchorPane panelAdicionarPeca;

    @FXML
    private AnchorPane panelEstatistica;

    @FXML
    private AnchorPane panelRemoverPeca;

    @FXML
    private RadioButton radioFaturamentoPeca;

    @FXML
    private RadioButton radioFaturamentoSessao;

    @FXML
    private TextArea taSinopse;

    @FXML
    private TextField tfFaturamentoMedio;

    @FXML
    private TextField tfNomePeca;

    @FXML
    void cadastrarPeca(ActionEvent event) {

    }

    @FXML
    void cadastrarSessao(ActionEvent event) {

    }

    @FXML
    void desabilitarSessao(ActionEvent event) {

    }

    @FXML
    void entrarAdicionarPeca(ActionEvent event) {

    }

    @FXML
    void entrarEstatistica(ActionEvent event) {

    }

    @FXML
    void entrarRemoverPeca(ActionEvent event) {

    }

    @FXML
    void habilitarSessao(ActionEvent event) {

    }

    @FXML
    void voltarMenuInicial(ActionEvent event) throws IOException {
        SceneSwap.setRoot(MainApp.getScene(), "telaInicial");
    }

}
