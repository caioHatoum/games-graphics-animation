package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



//classe que pega spritesheet da path indicada no metodo SpriteSheet através do param path,
//getSprite pega a spritesheet setada pela SpriteSheet e retorna o objeto instanciado.

public class SpriteSheet {
	private BufferedImage spritesheet;
	
	
	public SpriteSheet(String path) {
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage getSprite(int x, int y,int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
	
}
