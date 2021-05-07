package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class JogoFlappyBird extends ApplicationAdapter {

	//Construção da aplicação
	private SpriteBatch batch;
	private Texture passaro;//textura
	private Texture fundo;//textura

	//Variáveis tela
	private float larguraDispositivo;
	private float alturaDispositivo;

	//Movimentação
	private int movimentaY = 0;
	private int movimentaX = 0;


	@Override
	public void create () {

		batch = new SpriteBatch();
		passaro = new Texture("passaro1.png");
		fundo = new Texture("fundo.png");

		larguraDispositivo = Gdx.graphics.getWidth();//pegando informaçoes do dispositivo
		alturaDispositivo = Gdx.graphics.getHeight();//pegando informaçoes do dispositivo


	}

	@Override
	public void render () {

		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);//para aparecer na tela
		batch.draw(passaro, 50, 50, movimentaX, movimentaY);//para aparecer na tela

		movimentaY++;//para movimentar em Y
		movimentaX++;//para movimentar em X

		batch.end();
	}
	
	@Override
	public void dispose () {


	}
}
