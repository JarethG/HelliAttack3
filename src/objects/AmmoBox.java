package objects;

public class AmmoBox extends AnimatedObject{

    public AmmoBox(int x, int y, int id, int width, int height) {
        super(x, y, id, width, height);
    }

    public void update(Block[][] level){
        this.y+=1;
        for(int y = 0; y < level.length;y++){
            for(int x = 0; x < level[y].length;x++){
                if(level[y][x].getId()!=0 &&
                        getBounds().intersects(level[y][x].getBounds())){
                    snap(level[y][x]);
                }
            }
        }
    }
}
