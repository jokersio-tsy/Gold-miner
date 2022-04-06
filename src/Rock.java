import java.awt.Toolkit;

public class Rock extends Object{
	Rock()
	{
		this.score=1;
		this.type=2;
		this.x=(int)(Math.random()*700);
		this.y=(int)(Math.random()*450+300);
		this.width=71;
		this.height=71;
		this.m=100;
		this.img=Toolkit.getDefaultToolkit().getImage("imgs/rock1.png");
	}
}
