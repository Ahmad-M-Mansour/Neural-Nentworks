package networkneural;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.ArrayList;
public class NETWORK {
        public Scanner in=new Scanner(System.in);
        public final int  EPOCH=10000;
        public double E;
        public double[][] input;
        public int[] Classify;
        public double[][] output;        
        public double[][] weight;
        public double[] hidden;
        public double[] Context_Leyar;
        public int[] w_Hid_Con;
        public double[][] w_Con_Hid;
        public double[][] Vweight;
        public double[][] wSOM[];
        public double[][] E_Distance;
        public int R;
        public double[][] target;
        public double theta;
        public double Const_learn;
        public double Bias;
        public double ERROR;
        //Empty Constructor
        public NETWORK(){}
        //Constructor With Theta
        public NETWORK(int x,int y,int n,double c,double q)
        {
            input=new double[x][n];
            weight=new double[x][y];
            output=new double[y][n];
            Const_learn=c;
            theta=q;
        }
        //Constructor Without Theta
        public NETWORK(int x,int y,int n,double c)
        {
            input=new double[x][n];
            weight=new double[x][y];
            output=new double[y][n];
            Const_learn=c;
        }
        //Constructor With Hidden layer
        public NETWORK(int x,int y,int h,int n,double c)
        {
            input=new double[x][n];
            hidden=new double[h];
            Vweight=new double[x][h];
            weight=new double[h][y];
            output=new double[y][n];
            Const_learn=c;
            //this part to Elman
            Context_Leyar=new double[h];
            w_Con_Hid=new double[h][h];
            w_Hid_Con=new int[h];
        }
        //Constructor for SOM Neural Network
        public NETWORK(int x,int y1,int y2,int n,int r,double c)
        {
            input=new double[x][n];
            Classify=new int[n];
            output=new double[y1][y2];
            wSOM=new double[x][y1][y2];
            E_Distance=new double[y1][y2];
            R=r;
            Const_learn=c;
        }
        //Initialization Weight
        public void Initial_Weight(int x,int y)
        {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    weight[i][j]=((Math.random()*4.8)-2.4)/150.0;
                }
            }
        }
        //Linear function
        public double LINEAR(double net)
        {
         return net;
        }
        //Sign function
        public double SIGN(double net)
        {
           if(net>=theta)
               return 1;
           return -1;
        }
        //Step function
        public double STEP(double net)
        {
           if(net>=theta)
               return 1;
           return 0;
        }
        
        public double BIPOLAR(double net)
        {
           return ((2/(1+Math.exp(-net)))-1);
        }
        //sigmoid function
        public double UNBIPOLAR(double net)
        {
           return (1/(1+Math.exp(-net)));
        }
        //Sing function to Hopfield
        public int SIGN_HOP(int i) 
        {
            if(i<0)
                return -1;
            else if(i>0)
                return 1;
            else return i;
        
        }
        //check for Hopfield Network
        public boolean Check(int[] Y ,int[] Y1,int[] Y2)
        {
           if(Y[0]==Y1[0])
                for (int i = 1; i < 3; i++) {
                    if(Y[i]!=Y1[i])
                        return false;
                }
           else if(Y[0]==Y2[0])
                for (int i = 1; i < 3; i++) {
                    if(Y[i]!=Y2[i])
                        return false;
                }
           else return false;
           
        return true;
    }
        //Target output
        public void TARGET(int n,int z,double[][] d)
        {
            target=new double[n][z];
            for (int i = 0; i < n; i++) 
                for(int j = 0; j < z; j++)
                    target[i][j]=d[i][j];
        }
        public boolean EqualeP(int a,int b,double o)
        {           
                if(target[a][b]!=o)
                    return false;
            return true;
        }
        public boolean EqualeD(int a,int b,double o)
        {           
                if((target[a][b]-o)!=0.0001)
                    return false;         
            return true;
        }
        public boolean EqualeALLP(int a,int b,double[][] o)
        {       
            for (int i = 0; i < a; i++) 
                for (int j = 0; j < b; j++) 
                    if(target[i][j]!=o[i][j])
                    return false;          
            return true;         
        }
        public boolean EqualeALLD(int a,int b,double[][] o)
        {  
            double rms=0;
            for (int i = 0; i < a; i++){ 
                for (int j = 0; j < b; j++) {
                    rms=rms+Math.pow(target[i][j]-o[i][j],2);
                }}
            rms=Math.sqrt(rms);
            rms=rms/b;
            System.out.println("Error="+rms);
            if(rms>E)
                return false;
            return true;
                          
        }
        public void TESTING_P(int x,int y)
        {   double net;
            System.out.println("_____TISTING_____");
            for (int i = 0; i < y; i++) {
                net=0;
                for (int j = 0; j < x; j++) {
                    System.out.print("input["+j+"]=");
                    input[j][0]=in.nextDouble();
                    net=net+input[j][0]*weight[j][i];
                }
               // net=net+Bias;
                System.out.println("output["+i+"]="+STEP(net));
            }
            System.out.println("********************");
        }
        public void TESTING_D(int x,int y)
        {   double net;
            System.out.println("_____TISTING_____");
            for (int i = 0; i < y; i++) {
                net=0;
                for (int j = 0; j < x; j++) {
                    System.out.print("input["+j+"]=");
                    input[j][0]=in.nextDouble();
                    net=net+input[j][0]*weight[j][i];
                }
               // net=net+Bias;
                System.out.println("output["+i+"]="+BIPOLAR(net));
            }
            System.out.println("********************");
        }
        public void TESTING_BP_SIN(int x,int h)
        {
            double net;
            System.out.println("_____TISTING_____");
            for (int j = 0; j < x; j++) {
                    System.out.print("input["+j+"]=");
                    input[j][0]=in.nextDouble();
            }
            for (int i = 0; i < h; i++) {
                net=0;
                for (int j = 0; j < x; j++) {
                    net=net+input[j][0]*Vweight[j][i];
                }
               // net=net+Bias;
                hidden[i]=BIPOLAR(net);
            }
            net=0;
            for (int i = 0; i < h; i++) 
            { net=net+hidden[i]*weight[i][0];}
           // net=net+Bias;
                System.out.println("output="+BIPOLAR(net));
                System.out.println("********************");
        }
        public void TESTING_BP_LETTER(int x,int h,int y)
        {
            double net;
            System.out.println("_____TISTING_____");
            for (int j = 0; j < x; j++) {
                    System.out.print("input["+j+"]=");
                    input[j][0]=in.nextDouble();
            }
            for (int i = 0; i < h; i++) {
                net=0;
                for (int j = 0; j < x; j++) {
                    net=net+input[j][0]*Vweight[j][i];
                }
               // net=net+Bias;
                hidden[i]=UNBIPOLAR(net);
            }
            for (int i = 0; i < y; i++) {
            net=0;
            for (int j = 0; j < h; j++) 
            { net=net+hidden[j]*weight[j][i];}
           // net=net+Bias;
                System.out.println("output="+UNBIPOLAR(net));
                System.out.println("********************");
            }
        }
        public void TESTING(int x,int h,int y)
        {
            double net;
            System.out.println("_____TISTING_____");
            for (int j = 0; j < x; j++) {
                    System.out.print("input["+j+"]=");
                    input[j][0]=in.nextDouble();
            }
            for (int i = 0; i < h; i++) {
                net=0;
                for (int j = 0; j < x; j++) {
                    net=net+input[j][0]*Vweight[j][i];
                }
               // net=net+Bias;
                hidden[i]=UNBIPOLAR(net);
            }
            for (int i = 0; i < y; i++) {
            net=0;
            for (int j = 0; j < h; j++) 
            { net=net+hidden[j]*weight[j][i];}
           // net=net+Bias;
                System.out.println("output="+UNBIPOLAR(net));
                System.out.println("********************");
            }
        }
        public boolean SingelPerceptron(int n,int g)
        {
         double net;
         for (int i = 0; i < n; i++) {
             for (int j = 0; j < g; j++) {
                System.out.print("\nEnter input["+i+"]["+j+"]=");
                input[i][j]=in.nextDouble();
             }
         }
         for (int i = 0; i < n; i++) {
             weight[i][0]=Math.random();
         }
         for (int i = 0; i < g; i++) {
             System.out.print("\nEnter output["+0+"]["+i+"]=");
             output[0][i]=in.nextDouble();
         }
         TARGET(1,g, output);
         for (int k = 0; k < EPOCH; k++) { 
             System.out.println("____________________epoch:"+(k+1)+"____________________\nNEW weight:");
             for (int i = 0; i < n; i++) {
                for (int j = 0; j < 1; j++) {
                    System.out.print(weight[i][j]+"\t");
                }
               System.out.println(); 
            }
             for (int i = 0; i < g; i++) {
              net=0;
                for (int j = 0; j < n; j++) {
                    net+=input[j][i]*weight[j][0];
                }
               // net=net+Bias;
                    output[0][i]=STEP(net);
                }
          if(EqualeALLP(1,g,output))
                {
                    return true;
                }
         for (int i = 0; i < g; i++) {
             net=0;
             for (int j = 0; j < n; j++) {
                 net=net+input[j][i]*weight[j][0];
             }
             //net=net+Bias;
             output[0][i]=STEP(net);
             if(!EqualeP(0,i,output[0][i]))
                {
                    double r=target[0][i]-output[0][i];
                    for(int l = 0; l < n; l++) {
                       weight[l][0]=weight[l][0]+Const_learn*r*input[l][i];
                    }
                    //Bias=Bias+C*r;
                }
             }
         }
         return false;
       }
        public boolean SingelDelta(int n,int g)
        {
         double net;
         System.out.print("Enter ERROR=");
         ERROR=in.nextDouble();
         for (int i = 0; i < n; i++) {
             for (int j = 0; j < g; j++) {
                System.out.print("\nEnter input["+i+"]["+j+"]=");
                input[i][j]=in.nextDouble();
             }
         }
         for (int i = 0; i < n; i++) {
             weight[i][0]=Math.random()*2-1;
         }
         for (int i = 0; i < g; i++) {
             System.out.print("\nEnter output["+0+"]["+i+"]=");
             output[0][i]=in.nextDouble();
         }
         TARGET(1,g, output);
         for (int k = 0; k < EPOCH; k++) { 
             System.out.println("____________________epoch:"+(k+1)+"____________________\nNEW weight:");
             for (int i = 0; i < n; i++) {
                for (int j = 0; j < 1; j++) {
                    System.out.print(weight[i][j]+"\t");
                }
               System.out.println(); 
            }
             for (int i = 0; i < g; i++) {
              net=0;
                for (int j = 0; j < n; j++) {
                    net+=input[j][i]*weight[j][0];
                }
                    net+=Bias;
                    output[0][i]=BIPOLAR(net);
                }
          if(EqualeALLD(1,g,output))
                {
                    return true;
                }
         for (int i = 0; i < g; i++) {
             net=0;
             //int a=0;
             for (int j = 0; j < n; j++) {
                 net=net+input[j][i]*weight[j][0];
             }
             net=net+Bias;
             output[0][i]=BIPOLAR(net);
             if(!EqualeD(0,i,output[0][i]))
                {
                    //a++;
                    double r=0.5*(target[0][i]-output[0][i])*(1-Math.pow(output[0][i],2));
                    for(int l = 0; l < n; l++) {
                       weight[l][0]=weight[l][0]+Const_learn*r*input[l][i];
                    }
                    Bias=Bias+Const_learn*r;
                }
             //else if(a==0) return true;
             }
         }
         return true;
       }
        public boolean R_CategoryPerceptron(int x,int y,int g)
        {
          double net;
          for (int i = 0; i < x; i++) {
             for (int j = 0; j < g; j++) {
                System.out.print("\nEnter input["+i+"]["+j+"]=");
                input[i][j]=in.nextDouble();
             }
         }
         for (int i = 0; i < x; i++) {
             for (int j = 0; j < y; j++) {
               weight[i][j]=Math.random();
             }
         }
         for (int i = 0; i < y; i++) {
             for (int j = 0; j < g; j++) {
             System.out.print("\nEnter output["+i+"]["+j+"]=");
             output[i][j]=in.nextDouble();
             }
         }
         TARGET(y,g, output);
         for (int k = 0; k < EPOCH; k++) { 
             System.out.println("____________________epoch:"+(k+1)+"____________________\nNEW weight:");
             for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    System.out.print(weight[i][j]+"\t");
                }
               System.out.println();
            }
              for (int i = 0; i < g; i++) {
                 for (int h = 0; h < y; h++) {
                     net=0;
                     for (int j = 0; j < x; j++) {
                        net=net+input[j][i]*weight[j][h];
                 }
//                     net=net+Bias;
                     output[h][i]=STEP(net);
                 }
           }
          if(EqualeALLP(y,g,output))
                {
                    return true;
                }
             for (int i = 0; i < g; i++) {
                 for (int h = 0; h < y; h++) {
                     net=0;
                     for (int j = 0; j < x; j++) {
                        net=net+input[j][i]*weight[j][h];
                     }
//                     net=net+Bias;
                     output[h][i]=STEP(net);
                     if(!EqualeP(h,i,output[h][i]))
                     {
                       double r=target[h][i]-output[h][i];
                       for(int l = 0; l < x; l++) {
                       weight[l][h]=weight[l][h]+Const_learn*r*input[l][i];
                      }
//                       Bias=Bias+C*r;
                    }
                 }
             }          
         }
         return false;     
  }
        public boolean R_CategoryDelta(int x,int y,int g)
        {
          double net;
           System.out.print("Enter ERROR=");
           ERROR=in.nextDouble();
          for (int i = 0; i < x; i++) {
             for (int j = 0; j < g; j++) {
                System.out.print("\nEnter input["+i+"]["+j+"]=");
                input[i][j]=in.nextDouble();
             }
         }
         for (int i = 0; i < x; i++) {
             for (int j = 0; j < y; j++) {
               weight[i][j]=Math.random();
             }
         }
         for (int i = 0; i < y; i++) {
             for (int j = 0; j < g; j++) {
             System.out.print("\nEnter output["+i+"]["+j+"]=");
             output[i][j]=in.nextDouble();
             }
         }
         TARGET(y,g, output);
         for (int k = 0; k < EPOCH; k++) { 
             System.out.println("____________________epoch:"+(k+1)+"____________________\nNEW weight:");
             for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    System.out.print(weight[i][j]+"\t");
                }
               System.out.println(); 
            }
             for (int i = 0; i < g; i++) {
                 for (int h = 0; h < y; h++) {
                     net=0;
                     for (int j = 0; j < x; j++) {
                        net=net+input[j][i]*weight[j][h];
                 }
//                     net=net+Bias;
                     output[h][i]=BIPOLAR(net);
                 }
           }
          if(EqualeALLD(y,g,output))
                {
                    return true;
                }
         for (int i = 0; i < g; i++) {
                 for (int h = 0; h < y; h++) {
                     net=0;
                     for (int j = 0; j < x; j++) {
                        net=net+input[j][i]*weight[j][h];
                     }
//                     net=net+Bias;
                     output[h][i]=BIPOLAR(net);
                     if(!EqualeD(h,i,output[h][i]))
                     {
                       double r=0.5*(target[h][i]-output[h][i])*(1-Math.pow(output[h][i],2));
                       for(int l = 0; l < x; l++) {
                       weight[l][h]=weight[l][h]+Const_learn*r*input[l][i];
                      }
//                       Bias=Bias+C*r;
                    }
                 }
             }           
         }
         return false;       
  }
        public boolean BackPropegation_SIN(int numHidden) throws ParserConfigurationException, SAXException, IOException
        {
            int m=0;
             System.out.print("\nEnter ERROR=");
             ERROR=in.nextDouble();
            double net,ErrorOUT,ErrorH[],rms=0;
            ErrorH=new double[numHidden];
//             System.out.print("\nEnter Bias weight=");
//             Bias=in.nextDouble();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder aw=dbFactory.newDocumentBuilder();
            Document doc = aw.parse("ex2.xml");
            NodeList nList = doc.getElementsByTagName("training");
//            System.out.println(nList.getLength());
            System.out.println();
            for (int i = 0; i < nList.getLength(); i++) {
		Node nNode = nList.item(i);
                Element eElement = (Element) nNode;
                for(int j = 0; j < 2; j++){
                    input[j][i]= Double.parseDouble(eElement.getElementsByTagName("x"+(j+1)).item(0).getTextContent());
                    System.out.println(input[j][i]);                   
                }
                    output[0][i]=Math.sin(4*input[0][i]*input[1][i]);
                            //Double.parseDouble(eElement.getElementsByTagName("y").item(0).getTextContent());
                    System.out.println(output[0][i]);
            }
            TARGET(1, nList.getLength(), output);
            for (int i = 0; i < numHidden; i++) {
             weight[i][0]=Math.random();
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < numHidden; j++) {
                    Vweight[i][j]=Math.random();
                }
            }
            for (int k = 0; k < EPOCH; k++) {
                m=0;
                rms=0;
               System.out.println("____________________epoch:"+(k+1)+"____________________\nNEW Hidden Vweight:");
               for (int i = 0; i < 2; i++) {
                for (int j = 0; j < numHidden; j++) {
                    System.out.print(Vweight[i][j]+"\t");
                }
               System.out.println();
               } 
               System.out.print("NEW Output weight:\n");
               for (int i = 0; i < numHidden; i++) {
                  System.out.print(weight[i][0]+"\t");
               }
               System.out.println();               
               for (int i = 0; i < nList.getLength(); i++) {
                 for (int h = 0; h < numHidden; h++) {
                     net=0;
                     for (int j = 0; j < 2; j++) {
                        net=net+input[j][i]*Vweight[j][h];
                     }
//                     net=net+Bias;
                     hidden[h]=BIPOLAR(net);
                 }
                     net=0;
                     for (int j = 0; j < numHidden; j++) {
                       net+=hidden[j]*weight[j][0];
                     }
//                     net=net+Bias;
                    output[0][i]=BIPOLAR(net);
                    for (int j = 0; j < nList.getLength(); j++) {
                       rms=rms+Math.pow(target[0][j]-output[0][j],2);
                   }
//                 System.out.println("Error="+rms);  
//                 if(Math.sqrt(rms)/nList.getLength()>0.1)
//                 {
                     ErrorOUT=(target[0][i]-output[0][i])*(1-Math.pow(output[0][i],2));
                     for (int j = 0; j < numHidden; j++) {
                         ErrorH[j]=(target[0][i]-(ErrorOUT*weight[j][0]))*(1-Math.pow(hidden[j],2));
                     }
                     for (int j = 0; j < numHidden; j++) {
                         weight[j][0]=weight[j][0]+Const_learn*hidden[j]*ErrorOUT;
                     }
                     for (int j = 0; j < 2; j++) {
                         for (int l = 0; l < numHidden; l++) {
                             Vweight[j][l]=Vweight[j][l]+Const_learn*input[j][i]*hidden[j];
                         }
                     }
//                  }
//                 else{m++;}
//                 if(m==numHidden)return true;
                }
               
          if(EqualeALLD(1,nList.getLength(),output))
                {
                    return true;
                }
//              for (int i = 0; i < nList.getLength(); i++) {
//                 for (int h = 0; h < numHidden; h++) {
//                     net=0;
//                     for (int j = 0; j < 2; j++) {
//                        net=net+input[j][i]*Vweight[j][h];
//                     }
//                     net=net+Bias;
//                     hidden[h]=BIPOLAR(net);
//                     }
//                     net=0;
//                     for (int j = 0; j < numHidden; j++) {
//                       net+=hidden[j]*weight[j][0];
//                     }
//                     output[0][i]=BIPOLAR(net);
//                     if(!EqualeD(0,i,output[0][i]))
//                     {
//                         Error=(1-Math.pow(output[0][i],2))*(target[0][i]-output[0][i]);
//                         for (int j = 0; j < numHidden; j++) {
//                             ErrorH[j]=(1-Math.pow(hidden[j],2))*Error*weight[j][0];
//                         }
//                         for(int l = 0; l < numHidden; l++) {
//                           weight[l][0]=weight[l][0]+C*Error*hidden[l];
//                         }
//                         for (int h = 0; h < numHidden; h++) {
//                           for(int l = 0; l < 2; l++) {
//                              //r=0.5*(target[0][i]-output[0][i])*weight[l][0]*(1-Math.pow(hidden[l],2));
//                              Vweight[l][h]=Vweight[l][h]+C*ErrorH[h]*input[l][i];                           
//                             
//                           }
//                         }
//                     }    
//                          Bias=Bias+C*0.5;
//            }
            
              
        }
       
      return true;
   }
        public boolean BackPropegation_LETTERs(int numHidden) throws ParserConfigurationException, SAXException, IOException
        {
            int m=0;
            int y=5;
            double net,r,rms=0;
            double ErrorOUT[]=new double[y];
            double ErrorTOT[]=new double[numHidden];
            double ErrorH[]=new double[numHidden];
            String s[]=new String[5];
            s[0]="A";
            s[1]="C";
            s[2]="L";
            s[3]="O";
            s[4]="V";
            System.out.print("\nEnter theta=");
            theta=in.nextDouble();
             System.out.print("Enter ERROR=");
             ERROR=in.nextDouble();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder aw=dbFactory.newDocumentBuilder();
            Document doc = aw.parse("ex1.xml");
            NodeList nList = doc.getElementsByTagName("training");
//            System.out.println("number training="+nList.getLength());
            System.out.println();
            for (int i = 0; i < nList.getLength(); i++) {
		Node nNode = nList.item(i);
                Element eElement = (Element) nNode;
                for(int j = 0; j < 25; j++){
                    input[j][i]= Double.parseDouble(eElement.getElementsByTagName("x"+(j+1)).item(0).getTextContent());
                    System.out.println(input[j][i]);                   
                }
                for (int j = 0; j < y; j++) {
                    output[j][i]=Double.parseDouble(eElement.getElementsByTagName("y"+(j+1)).item(0).getTextContent());
                    System.out.println(s[j]+"="+output[j][i]);
                 }
            }
//             for (int i = 0; i < nList.getLength(); i++) {
//                 Node nNode = nList.item(i);
//                Element eElement = (Element) nNode;
//                 for (int j = 0; j < 1; j++) {
//                    output[j][i]=Double.parseDouble(eElement.getElementsByTagName("y"+(j+1)).item(0).getTextContent());
//                    System.out.println(s[j]+"="+output[j][i]);
//                 }
//             }
            TARGET(y, nList.getLength(), output);
            for (int i = 0; i < numHidden; i++) {
                for (int j = 0; j < y; j++) {
             weight[i][j]=Math.random();
                }
            }
            for (int i = 0; i < 25; i++) {
                for (int j = 0; j < numHidden; j++) {
                    Vweight[i][j]=Math.random();
                }
            }
            for (int k = 0; k < 50000; k++) {
               System.out.println("____________________epoch:"+(k+1)+"____________________");
//               for (int i = 0; i < 25; i++) {
//                for (int j = 0; j < 2; j++) {
//                    System.out.println("NEW Hidden Vweight:\n"+Vweight[i][j]);
//                }
//               System.out.println();
//               } 
//               System.out.print("NEW Output weight:\n");
//               for (int i = 0; i < 2; i++) {
//                   for (int j = 0; j < 15; j++) {
//                  System.out.print(weight[i][j]+"\n");
//                  }
//               }
               System.out.println();
               for (int i = 0; i < nList.getLength(); i++) {
                 for (int h = 0; h < numHidden; h++) {
                     net=0;
                     for (int j = 0; j < 25; j++) {
                        net=net+input[j][i]*Vweight[j][h];
                     }
//                     net=net+Bias;
                     hidden[h]=UNBIPOLAR(net);
                 }
                   for (int l= 0; l < y; l++) {
                     net=0;
                     for (int j = 0; j < numHidden; j++) {
                       net+=hidden[j]*weight[j][l];
                     }
                    output[l][i]=UNBIPOLAR(net);
                   }
//                    for (int j = 0; j < nList.getLength(); j++) {
//                       rms=rms+Math.pow(target[0][j]-output[0][j],2);
//                   }
////                 System.out.println("Error="+rms);  
//                 if(Math.sqrt(rms)/nList.getLength()>0.1)
//                 {
                   for (int j = 0; j < y; j++) {
                     ErrorOUT[j]=(target[j][i]-output[j][i])*output[j][i]*(1-output[j][i]);
                   }
                   for (int w = 0; w < y; w++) {
                       for (int j = 0; j < numHidden; j++) {
                           ErrorTOT[j]=ErrorOUT[w]*weight[j][w];
                       }
                   }
                     for (int j = 0; j < numHidden; j++) {
                         ErrorH[j]=(hidden[j])*(1-hidden[j])*(ErrorTOT[j]);
                     }
                   
                   for (int w = 0; w < y; w++) {
                     for (int j = 0; j < numHidden; j++) {
                         weight[j][w]=weight[j][w]+Const_learn*hidden[j]*ErrorOUT[w];
                     }
                   }
                     for (int j = 0; j < 25; j++) {
                         for (int l = 0; l < numHidden; l++) {
                             Vweight[j][l]=Vweight[j][l]+Const_learn*input[j][i]*ErrorH[l];
                         }
                     }
//                  }
//                 else{m++;}
//                 if(m==numHidden)return true;
                 }
               if(EqualeALLD(y,nList.getLength(),output))
                {
                    return true;
                }
            }
//          if(EqualeALLD(2,nList.getLength(),output))
//                {
//                    return true;
//                }
//        for (int i = 0; i < nList.getLength(); i++) {
//                 for (int h = 0; h < numHidden; h++) {
//                     net=0;
//                     for (int j = 0; j < 25; j++) {
//                        net=net+input[j][i]*Vweight[j][h];
//                     }
////                     net=net+Bias;
//                     hidden[h]=BIPOLAR(net);
//                 
//                   for (int v= 0; v < 2; v++) {
//                     net=0;
//                     for (int j = 0; j < numHidden; j++) {
//                       net+=hidden[j]*weight[j][v];
//                     }
//                    output[v][i]=BIPOLAR(net);
//                   
//                   
//                     if(!EqualeD(v,i,output[v][i]))
//                     {
//                       r=target[v][i]-output[v][i];
//                       for(int l = 0; l < numHidden; l++) {
//                       weight[l][v]=weight[l][v]+C*r*hidden[l];
//                       }
//                       for(int l = 0; l < 25; l++) {
//                           r=(target[v][i]-output[v][i]);
//                           Vweight[l][h]=Vweight[l][h]+C*r*input[l][i];
//                      }
////                       Bias=Bias+C*r;
//                    }
//                 }
//               }           
//              }
//            }
            return true;
  }
        public boolean BackPropagation_IRIS(int numHidden) throws FileNotFoundException
        {
            System.out.print("\nEnter Error=");
            E=in.nextDouble();
          File newFile=new File("IRIS_SOM.txt");
          double net=0,ErrorOUT;
          Scanner cdc=new Scanner(newFile);
          int s=0;
          double[] ErrorH=new double[numHidden];
          String a,b,c,d,z;
          for (int j = 0; !cdc.hasNext("@"); j++) {
            s++;
            a=cdc.next();
            b=cdc.next();
            c=cdc.next();
            d=cdc.next();
            z=cdc.next();
            input[0][j]= Double.parseDouble(a);
            input[1][j]= Double.parseDouble(b);
            input[2][j]= Double.parseDouble(c);
            input[3][j]= Double.parseDouble(d);
                    if(z.equals("Iris-setosa"))
                        output[0][j]=1;
                    if(z.equals("Iris-versicolor"))
                        output[0][j]=0;
                    if(z.equals("Iris-virginica"))
                        output[0][j]=-1;
          }
          cdc.close();
//             for (int i = 0; i < s; i++) {
//                      System.out.println(input[0][i]+" "+input[1][i]+" "+input[2][i]+" "+input[3][i]+" "+output[0][i]);
//             }
          
            TARGET(1, s, output);
            for (int i = 0; i < numHidden; i++) {
                for (int j = 0; j < 1; j++) {
                    weight[i][j]=((Math.random()*4.8)-2.4)/s;
                }
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < numHidden; j++) {
                    Vweight[i][j]=((Math.random()*4.8)-2.4)/s;
                }
            }
            for (int k = 0; k < EPOCH; k++) {
               System.out.println("____________________epoch:"+(k+1)+"____________________");
               for (int i = 0; i < s; i++) {
                 for (int h = 0; h < numHidden; h++) {
                     net=0;
                     for (int j = 0; j < 4; j++) {
                        net=net+input[j][i]*Vweight[j][h];
                     }
//                     net=net+Bias;
                     hidden[h]=UNBIPOLAR(net);
                     }
                     net=0;
                 for (int j = 0; j < numHidden; j++) {
                   net+=hidden[j]*weight[j][0];
                 }
                 output[0][i]=UNBIPOLAR(net);
                 if(!EqualeD(0,i,output[0][i]))
                {
                    ErrorOUT=output[0][i]*(1-output[0][i])*(target[0][i]-output[0][i]);
                     for (int j = 0; j < numHidden; j++) {
                         ErrorH[j]=hidden[j]*(1-hidden[j])*ErrorOUT*weight[j][0];
                     }
                     for (int j = 0; j < numHidden; j++) {
                         weight[j][0]=weight[j][0]+Const_learn*hidden[j]*ErrorOUT;
                     }
                     for (int j = 0; j < 4; j++) {
                         for (int l = 0; l < numHidden; l++) {
                             Vweight[j][l]=Vweight[j][l]+Const_learn*input[j][i]*ErrorH[l];
                         }
                     }
                } 
             }
              if(EqualeALLD(1, s, output))
              {
                  return true;
              }
               
         }
         return false;
      }
        public boolean Elman(int x,int y,int h,int n)
        {
            double net=0,ErrorOUT;
            double[] ErrorH=new double[h];
            System.out.print("\nEnter Error=");
            E=in.nextDouble();
            for (int i = 0; i < h; i++) {
                    w_Hid_Con[i]=1;
            }
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < h; j++) {
                    w_Con_Hid[i][j]=Math.random()*2-1;
                }
            }
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < y; j++) {
                    weight[i][j]=Math.random()*2-1;
                }
            }
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < h; j++) {
                    Vweight[i][j]=Math.random()*2-1;
                }
            }
            input[0][0]=0.0;
            output[0][0]=0.0;
            for (int i = 1; i < n; i++) {
                input[0][i]=0.1+input[0][i-1];
            }
            for (int i = 1; i < n; i++) {
                output[0][i]=0.2*output[0][i-1];
            }
            TARGET(1, n, output);
            for (int k = 0; k < EPOCH; k++) {
               System.out.println("____________________epoch:"+(k+1)+"____________________");
               for (int i = 0; i < n; i++) {
                  for (int z = 0; z < h; z++) {
                     net=0;
                     for (int j = 0; j < x; j++) {
                        net=net+input[j][i]*Vweight[j][z];
                     }
                      for (int j = 0; j < h; j++) {
                          net=net+Context_Leyar[j]*w_Con_Hid[j][z];
                      }
                     hidden[z]=BIPOLAR(net);
                     }
                     net=0;
                 for (int j = 0; j < h; j++) {
                   net+=hidden[j]*weight[j][0];
                 }
                 output[0][i]=LINEAR(net);
                   for (int j = 0; j < h; j++) {
                       Context_Leyar[j]=hidden[j]*w_Hid_Con[j];
                   }
                 if(!EqualeD(0,i,output[0][i]))
                {
                    ErrorOUT=(target[0][i]-output[0][i]);
                     for (int j = 0; j < h; j++) {
                         ErrorH[j]=hidden[j]*(1-hidden[j])*ErrorOUT*(weight[j][0]+w_Con_Hid[j][0]);
                     }
                     for (int j = 0; j < h; j++) {
                         weight[j][0]=weight[j][0]+Const_learn*hidden[j]*ErrorOUT;
                     }
                     for (int j = 0; j < x; j++) {
                         for (int l = 0; l < h; l++) {
                             Vweight[j][l]=Vweight[j][l]+Const_learn*input[j][i]*ErrorH[l];
                         }
                     }
                     for (int j = 0; j < h; j++) {
                         for (int l = 0; l < h; l++) {
                             w_Con_Hid[j][l]=w_Con_Hid[j][l]+Const_learn*Context_Leyar[j]*ErrorH[l];
                         }
                    }
                }
             }
              if(EqualeALLD(1, n, output))
              {
                  return true;
              }
               
         }
         return false;
        }
        public void Hopfield()
        {
            int Y1[],Y2[],W[][],I[][],M,X[],Y[];
            Scanner n=new Scanner(System.in);
            boolean test;
            M=2;
            Y1=new int[]{1,1,1};
            Y2=new int[]{-1,-1,-1};
            W=new int[3][3];
            X=new int[3];
            Y=new int[3];
            I=new int[][]{{1,0,0},{0,1,0},{0,0,1}};
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    W[i][j]=Y1[i]*Y1[j]+Y2[i]*Y2[j]-M*I[i][j];
                }
            }
           System.out.println("--------------------"); 
                test=true;
                while(test){
                    for (int i = 0; i < 3; i++) {
                        Y[i]=0;
                    }
                    int t;
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1){
                        for (int i = 0; i < 3; i++) {
                           System.out.print("x["+i+"]=");
                           X[i]=n.nextInt();
                        }
                        for (int i = 0; i < 3; i++) {
                           for (int j = 0; j < 3; j++) {
                              Y[i]+=W[i][j]*X[j]-0;
                           }
                         Y[i]=SIGN_HOP(Y[i]);
                       }
                       while(!Check(Y,Y1,Y2)){
                             for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {
                                       Y[i]+=W[i][j]*Y[j]-0;
                                }
                                Y[i]=SIGN_HOP(Y[i]);
                             }  
                       }
                       for (int i = 0; i < 3; i++) {
                            System.out.print(Y[i]+" ");
                       }
                       System.out.println("\n--------------------");
                    }
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
            System.out.println();
        }
        public boolean SOM(int x ,int Yi ,int Yj,int r,double A) throws FileNotFoundException
        {
          File newFile=new File("IRIS_SOM.txt");
          Scanner cdc=new Scanner(newFile);
          int s=0, min_I=0,min_J=0;
          double sum=0, MIN_ED=Double.MAX_VALUE;
          String a,b,c,d,z;
          for (int j = 0; !cdc.hasNext("@"); j++) {
            s++;
            a=cdc.next();
            b=cdc.next();
            c=cdc.next();
            d=cdc.next();
            z=cdc.next();
            input[0][j]= Double.parseDouble(a);
            input[1][j]= Double.parseDouble(b);
            input[2][j]= Double.parseDouble(c);
            input[3][j]= Double.parseDouble(d);
            switch (z) {
                case "Iris-setosa":
                    Classify[j]=1;
                    break;
                case "Iris-versicolor":
                    Classify[j]=2;
                    break;
                case "Iris-virginica":
                    Classify[j]=3;
                    break;
            }
          }
          cdc.close();
          
             for(int i=0; i<x; i++)
                 for(int j1=0; j1<Yi; j1++)
                     for(int j2=0; j2<Yj; j2++)
                        { 
                               wSOM[i][j1][j2]=Math.random()*2-1;
                        }
            for(int j1=0; j1<Yi; j1++)
                    for(int j2=0; j2<Yj; j2++)
                        output[j1][j2]=0;
            for (int k = 0; k < 100; k++) {
               System.out.println("____________________epoch:"+(k+1)+"____________________");
                for (int w = 0; w < s; w++) {
                    R=r;
                    Const_learn=A;
                    for(int j1=0; j1<Yi; j1++){
                       for(int j2=0; j2<Yj; j2++){ 
                          for(int i=0; i<x; i++){
                              sum+=Math.pow(input[i][w]-wSOM[i][j1][j2],2);
                          }
                          E_Distance[j1][j2]=Math.sqrt(sum);
                          sum=0;
                        }
                     }
                    //
                    for(int j1=0; j1<Yi; j1++){
                       for(int j2=0; j2<Yj; j2++){
                           if(E_Distance[j1][j2] < MIN_ED){
                               MIN_ED=E_Distance[j1][j2];
                               min_I=j1;
                               min_J=j2;
                           }
                       }
                    }
                    
                    for (int i = 0; i < x; i++) {
                        wSOM[i][min_I][min_J]=wSOM[i][min_I][min_J]+Const_learn*(input[i][w]-wSOM[i][min_I][min_J]);
                        if(R!=0){
                              if((min_I-R <Yi)&&(min_I-R >0))
                                  wSOM[i][min_I-R][min_J]=wSOM[i][min_I-R][min_J]+Const_learn*(input[i][w]-wSOM[i][min_I-R][min_J]);
                              if((min_J-R <Yj)&&(min_J-R >0))
                                  wSOM[i][min_I][min_J-R]=wSOM[i][min_I][min_J-R]+Const_learn*(input[i][w]-wSOM[i][min_I][min_J-R]);
                              if(min_I+R <Yi)
                                  wSOM[i][min_I+R][min_J]=wSOM[i][min_I+R][min_J]+Const_learn*(input[i][w]-wSOM[i][min_I+R][min_J]);
                              if(min_J+R <Yj)
                                  wSOM[i][min_I][min_J+R]=wSOM[i][min_I][min_J+R]+Const_learn*(input[i][w]-wSOM[i][min_I][min_J+R]);
                              if((min_I-R <Yi)&&(min_J-R <Yj)&&(min_I-R >0)&&(min_J-R >0))
                                  wSOM[i][min_I-R][min_J-R]=wSOM[i][min_I-R][min_J-R]+Const_learn*(input[i][w]-wSOM[i][min_I-R][min_J-R]);
                              if((min_I+R <Yi)&&(min_J+R <Yj))
                                  wSOM[i][min_I+R][min_J+R]=wSOM[i][min_I+R][min_J+R]+Const_learn*(input[i][w]-wSOM[i][min_I+R][min_J+R]);
                              if((min_I+R <Yi)&&(min_J-R <Yj)&&(min_J-R >0))
                                  wSOM[i][min_I+R][min_J-R]=wSOM[i][min_I+R][min_J-R]+Const_learn*(input[i][w]-wSOM[i][min_I+R][min_J-R]);
                              if((min_I-R <Yi)&&(min_J+R <Yj)&&(min_I-R >0))
                                  wSOM[i][min_I-R][min_J+R]=wSOM[i][min_I-R][min_J+R]+Const_learn*(input[i][w]-wSOM[i][min_I-R][min_J+R]);
                            
                        }
//                        else Const_learn=Const_learn/2;
                    }
//                    for(int i=0; i<x; i++)
//                      for(int j1=0; j1<Yi; j1++)
//                        for(int j2=0; j2<Yj; j2++)
//                        { 
////                            if((j1!=min_I)&&(j2!=min_J)&&(j1!=min_I-R)&&(j1!=min_I+R)&&(j2!=min_J-R)&&(j2!=min_J+R))
//                               wSOM[i][j1][j2]=Math.random();
//                        }
//                   if(output[min_I][min_J]==0)
                    output[min_I][min_J]=Classify[w];
                    MIN_ED=Double.MAX_VALUE;
                    min_I=0;
                    min_J=0;
                    R=R/2;
                    Const_learn=Const_learn/2;
                  }
            }
            for(int j1=0; j1<Yi; j1++){
                System.out.println();
                    for(int j2=0; j2<Yj; j2++)
                        System.out.print(output[j1][j2]+" ");
            }
            return true;
        }    
}