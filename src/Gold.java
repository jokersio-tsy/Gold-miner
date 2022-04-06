import java.awt.Toolkit;

public class Gold extends Object{
	Gold()
	{
		this.score=4;
		this.type=1;
		this.x=(int)(Math.random()*700);
		this.y=(int)(Math.random()*450+300);
		this.width=52;
		this.height=52;
		this.m=40;
		this.img=Toolkit.getDefaultToolkit().getImage("imgs/gold1.gif");
	}
}

class GoldMini extends Gold{
	GoldMini()
	{
		this.score=2;
		this.width=36;
		this.height=36;
		this.m=15;
		this.img=Toolkit.getDefaultToolkit().getImage("imgs/gold0.gif");
	}
}

class GoldPlus extends Gold{
	GoldPlus()
	{
		this.score=8;
		this.x=(int)(Math.random()*650);
		this.width=105;
		this.height=105;
		this.m=70;
		this.img=Toolkit.getDefaultToolkit().getImage("imgs/gold2.gif");
	}
}