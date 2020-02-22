package Scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.salyin.muzos.Main;

import Screens.PlayScreen;


public class HudControllers implements Disposable {
    public Stage stage;
    public Viewport viewport;
    private ImageButton btIzq;
    private ImageButton btDer;
    private ImageButton btJump;


    public HudControllers(SpriteBatch sb) {


        Texture leftArrow = new Texture("sprites/hud/hud_controllers/leftArrow.png");
        Texture rightArrow = new Texture("sprites/hud/hud_controllers/rightArrow.png");
        Texture jumpButton = new Texture("sprites/hud/hud_controllers/jumpBt.png");
        Drawable leftArrowImg = new TextureRegionDrawable(new TextureRegion(leftArrow));
        Drawable rightArrowImg = new TextureRegionDrawable(new TextureRegion(rightArrow));
        Drawable jumpButtonImg = new TextureRegionDrawable(new TextureRegion(jumpButton));

        Skin skin = new Skin();
        skin.add("leftButton", leftArrowImg, Drawable.class);
        skin.add("rightButton", rightArrowImg, Drawable.class);
        skin.add("jumpButton", jumpButtonImg, Drawable.class);

        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle btnStyle2 = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle btnStyle3 = new ImageButton.ImageButtonStyle();
        btnStyle.imageUp = skin.getDrawable("leftButton");
        btnStyle2.imageUp = skin.getDrawable("rightButton");
        btnStyle3.imageUp = skin.getDrawable("jumpButton");
        btIzq = new ImageButton(btnStyle);
        btDer = new ImageButton(btnStyle2);
        btJump = new ImageButton(btnStyle3);

        viewport = new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //TODO ARREGLAR BACKGROUND HUD
     //   texture = new Texture("sprites/hud/hud_controllers/test_hud.png");
     //   textR = new TextureRegion(texture);
     //   textRd = new TextureRegionDrawable(textR);


        Table tb = new Table();
        tb.bottom();
        tb.debug();
        tb.setFillParent(true);

        tb.add(btIzq);

        tb.add(btDer);

        tb.add(btJump);


        stage.addActor(tb);
    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
