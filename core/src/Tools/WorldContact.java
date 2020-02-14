package Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContact implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Esta colisionando?", "si");
        //TODO ESTO ES PARA EL DOBLE SALTO
        //player.setTocasuelo(true);

    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("Esta colisionando?" , "no");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
