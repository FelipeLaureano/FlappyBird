package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import javax.xml.soap.Text;

public class JogoFlappyBird extends ApplicationAdapter {

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

	//Movimentação
	//private int movimentaY = 0;
	//private int movimentaX = 0;

	@Override
	public void create () {

		inicializarTexturas();
		inicializarObjetos();

	}

	private void inicializarObjetos() {

		passaros = new Texture[3];//lista de texturas do passaro
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");

		canoBaixo = new Texture("cano_baixo_maior.png");
		canoTopo = new Texture("cano_topo_maior.png");
	}

	private void inicializarTexturas(){

		batch = new SpriteBatch();
		random = new Random();

		larguraDispositivo = Gdx.graphics.getWidth();//pegando informaçoes do dispositivo(propriedades da tela)
		alturaDispositivo = Gdx.graphics.getHeight();//pegando informaçoes do dispositivo(propriedades da tela)
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;//para aparecer no meio da tela
		posicaoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 150;//possível aumentar ou diminuir dificuldade do jogo
	}

	@Override
	public void render () {

		verificarEstadoJogo();
		desenharTexturas();
	}

	private void verificarEstadoJogo() {

		posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;
		if(posicaoCanoHorizontal < -canoBaixo.getWidth()){
			posicaoCanoHorizontal = larguraDispositivo;// ir até o final da tela
			posicaoCanoHorizontal = random.nextInt(400) -200;//variação
		}

		boolean toqueTela = Gdx.input.justTouched();
		if(Gdx.input.justTouched()){
			gravidade = -25;
		}
		if(posicaoInicialVerticalPassaro > 0 || toqueTela){
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;
		}

		variacao += Gdx.graphics.getDeltaTime() * 10;
		if(variacao >3){
			variacao = 0;
		}
		gravidade++;
		movimentaX++;
	}

	private void desenharTexturas(){
		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);//para aparecer na tela
		batch.draw(passaros[(int)variacao],200, posicaoInicialVerticalPassaro);//animação na tela

		batch.draw(canoBaixo, posicaoCanoHorizontal,
				alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical);
		batch.draw(canoTopo, posicaoCanoHorizontal,
				alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical);

		batch.end();

		//movimentaY++;//para movimentar em Y
		//movimentaX++;//para movimentar em X
	}

	@Override
	public void dispose () {


	}
}
