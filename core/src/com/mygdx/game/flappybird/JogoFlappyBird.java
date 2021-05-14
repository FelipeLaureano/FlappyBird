package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import javax.xml.soap.Text;

public class JogoFlappyBird extends ApplicationAdapter {

	private int pontos = 0;
	private int movimentaX = 0;
	private int gravidade = 0;

	//Construção da aplicação
	private SpriteBatch batch;
	private Texture[] passaros;//lista de texturas
	private Texture fundo;//textura
	private Texture canoTopo;
	private Texture canoBaixo;

	//Variáveis tela
	//float em todos para variar valor(usando multiplicador)
	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao = 0;
	private float posicaoInicialVerticalPassaro = 0;
	private float posicaoCanoHorizontal;
	private float espacoEntreCanos;
	private float posicaoCanoVertical;

	private Random random;
	BitmapFont textoPontuacao;
	private boolean passouCano = false;

	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoCima;
	private Rectangle retanguloCanoBaixo;

	//Movimentação
	//private int movimentaY = 0;
	//private int movimentaX = 0;

	@Override
	public void create () {

		inicializarTexturas();
		inicializarObjetos();

	}

	private void inicializarTexturas() {

		passaros = new Texture[3];//lista de texturas do passaro
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");

		canoBaixo = new Texture("cano_baixo_maior.png");
		canoTopo = new Texture("cano_topo_maior.png");
	}

	private void inicializarObjetos(){

		batch = new SpriteBatch();
		random = new Random();

		larguraDispositivo = Gdx.graphics.getWidth();//pegando informaçoes do dispositivo(propriedades da tela)
		alturaDispositivo = Gdx.graphics.getHeight();//pegando informaçoes do dispositivo(propriedades da tela)
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;//para aparecer no meio da tela
		posicaoCanoHorizontal = larguraDispositivo;//coloca no final (direita) da tela
		espacoEntreCanos = 350;//possível aumentar ou diminuir dificuldade do jogo

		//Criação do texto de pontuação:
		textoPontuacao = new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);
	}

	@Override
	public void render () {

		verificarEstadoJogo();
		validarPontos();
		desenharTexturas();
		detectarColisao();
	}

	private void detectarColisao(){
		circuloPassaro.set(200 + passaros[0].getWidth() / 2, //posição do pássaro + tamanho dele dividido pelo diametro
				posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2,
				passaros[0].getWidth() / 2);

		//Identificar tamanho da colisao
		retanguloCanoCima.set(posicaoCanoHorizontal,
				alturaDispositivo / 2 - canoTopo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical, canoTopo.getWidth(), canoTopo.getHeight());

		retanguloCanoBaixo.set(posicaoCanoHorizontal,
				alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical, canoBaixo.getWidth(), canoBaixo.getHeight());

		//Verificar se bateu
		boolean colisaoCanoCima = Intersector.overlaps(circuloPassaro, retanguloCanoCima);
		boolean colisaoCanoBaixo = Intersector.overlaps(circuloPassaro, retanguloCanoBaixo);

		if(colisaoCanoBaixo || colisaoCanoCima){
			Gdx.app.log("Log","Bateu");
		}
	}

	private void verificarEstadoJogo() {

		posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;//movimentação do cano
		if(posicaoCanoHorizontal < -canoBaixo.getWidth()){
			posicaoCanoHorizontal = larguraDispositivo;// ir até o final da tela
			posicaoCanoVertical = random.nextInt(400) -200;//variação de abertura
			passouCano = false;
		}

		boolean toqueTela = Gdx.input.justTouched();// toque na tela para "pular"
		if(Gdx.input.justTouched()){
			gravidade = -25;
		}
		if(posicaoInicialVerticalPassaro > 0 || toqueTela){
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;
		}

		variacao += Gdx.graphics.getDeltaTime() * 10;//variação de animação do personagem
		if(variacao >3){
			variacao = 0;
		}
		gravidade++;
		movimentaX++;
	}

	private void validarPontos(){
		//Verificar se passou pelo cano:
		if(posicaoCanoHorizontal < 200 - passaros[0].getWidth()){//posicionamento igual ao do pássaro na tela (200), menos o tamanho do pássaro
			if(!passouCano){
				pontos++;
				passouCano = true;
			}
		}
	}

	private void desenharTexturas(){
		//Iniciar desenho com batch.begin e terminar com batch.end
		batch.begin();

		//Desenho fundo e pássaro:
		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);//para aparecer na tela
		batch.draw(passaros[(int)variacao],200, posicaoInicialVerticalPassaro);//animação na tela

		//Desenho dos canos:
		batch.draw(canoBaixo, posicaoCanoHorizontal,
				alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical);
		batch.draw(canoTopo, posicaoCanoHorizontal,
				alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical);

		//Desenho da pontuação:
		textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo / 2, alturaDispositivo - 100);

		batch.end();

		//movimentaY++;//para movimentar em Y
		//movimentaX++;//para movimentar em X
	}

	@Override
	public void dispose () {


	}
}
