package juint.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.qiaosoftware.base.utils.SystemMessage;
import com.qiaosoftware.surveyproject.component.repository.UserDao;
import com.qiaosoftware.surveyproject.component.repository.impl.UserDaoImpl;
import com.qiaosoftware.surveyproject.entity.guest.Employee;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

public class NormalTest {
    
    @Test
    public void test10() {
        String adminName = SystemMessage.getString("admin.name");
        System.out.println(adminName);
    }
    
    //@Test
    public void test09() {
        for(int i = -50; i < 50; i++) {
            String tableName = DataProcessUtils.generateTableName(i);
            System.out.println(tableName);
        }
    }
    
    //@Test
    public void test08() {
        int i = 1;
        System.out.println("1左移30位："+(i << 30));
        System.out.println("1左移31位："+(i << 31));
        System.out.println("1左移32位："+(i << 32));
    }
    
    //@Test
    public void test07() {
        String servletPath = DataProcessUtils.cutServletPath("/guest/bag/removeBag");
        System.out.println(servletPath);
    }
    
    //@Test
    public void test06() {
        String string = DataProcessUtils.convertStringArr(new String[]{});
        System.out.println(string);
    }
    
    //@Test
    public void test05() {
        
        //源对象
        Employee source = new Employee(100, "empName100", 100);
        
        //目标对象
        Employee target = (Employee) DataProcessUtils.deeplyCopy(source);
        
        System.out.println("源对象hashCode\t"+source.hashCode());
        System.out.println("目标对象hashCode\t"+target.hashCode());
        
        System.out.println("源对象中的数据：\t"+source);
        System.out.println("目标对象的数据：\t"+target);
        
    }
    
    //@Test
    public void test04() {
        
        //源对象
        Employee source = new Employee(100, "empName100", 100);
        
        //目标对象
        Employee target = null;
        
        //准备工作
        //声明四个流对象的变量
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;
        
        try {
            //创建字节数组输出流
            baos = new ByteArrayOutputStream();
            
            //创建对象输出流
            oos = new ObjectOutputStream(baos);
            
            //将源对象通过对象输出流写入到字节数组中
            oos.writeObject(source);
            
            //从对象输出流中获取字节数组
            byte[] byteArray = baos.toByteArray();
            
            //根据字节数组创建字节数组输入流
            bais = new ByteArrayInputStream(byteArray);
            
            //让对象输入流从字节数组的输入流中读取数据
            ois = new ObjectInputStream(bais);
            
            //从对象输入流中反序列化取出对象：目标对象
            target = (Employee) ois.readObject();
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            
            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
        
        System.out.println("源对象hashCode\t"+source.hashCode());
        System.out.println("目标对象hashCode\t"+target.hashCode());
        
        System.out.println("源对象中的数据：\t"+source);
        System.out.println("目标对象的数据：\t"+target);
        
    }
    
    //@Test
    public void test03() {
        
        String source = "abc123";
        
        String md5 = DataProcessUtils.md5(source);
        
        System.out.println(md5);
        
    }
    
    //@Test
    public void test02() throws NoSuchAlgorithmException {
        
        //源字符串
        String source = "1234WWWWWWeee";
        
        //声明一个变量用于保存加密后的密文
        String secret = null;

        //获取源字符串对应的字节数组
        byte[] sourceByte = source.getBytes();
        
        //获取加密工具
        MessageDigest digest = MessageDigest.getInstance("md5");
        
        //将源字符串对应的字节数组加密为固定长度的字节数组
        byte[] byteArr = digest.digest(sourceByte);
        
        System.out.println(byteArr.length);
        
        //将加密后的字节数组转换为16进制字符组成的字符串
        char [] codeArr = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        
        //让每一个byteArr数组中的整数和15做与运算，就可以得到低四位对应的数值
        //让每一个byteArr数组中的整数先右移4伟，然后和15做与运算，就可以得到高四位对应的数值
        //不管高四位还是低四位取得的数值都正好可以作为codeArr数组的下标，那么以此得到的字符就是加密后的结果
        
        StringBuilder builder = new StringBuilder();
        
        for(int i = 0; i < byteArr.length; i++) {
            byte b = byteArr[i];
            //计算低四位
            int lowNum = b & 15;
            
            //计算高四位
            int highNum = (b >> 4) & 15;
            
            char lowChar = codeArr[lowNum];
            char highChar = codeArr[highNum];
            
            builder.append(highChar).append(lowChar);
        }
        
        System.out.println(builder.toString());
        
    }
    
    //@Test
    public void test01() {
        
        UserDao userDao = new UserDaoImpl();
        userDao.saveEntity(null);
        
    }
}
