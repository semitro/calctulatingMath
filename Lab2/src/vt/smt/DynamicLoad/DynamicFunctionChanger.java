package vt.smt.DynamicLoad;

import java.io.File;
import java.io.RandomAccessFile;
/**
 *  Класс для изменения кода функции в файле, подготовленном для компиляции
 */
public class DynamicFunctionChanger {
    public DynamicFunctionChanger(){

    }

    /**
     * @param code - код для функции. Предоставлять почти в математическом виде
     * ex: (sin(x)*x - 25)/15.0
    **/
    public void setCode(String code){
        // Защита от переполнения буфера или порчти файла
        if(code.length() > 125)
            return; //!
        // Работает с этим файлом
        File source = new File("DynamicFunction/Function.java");
        try {
            // Нам нужно изменить лишь одну строку где-то посередине
            RandomAccessFile file = new RandomAccessFile(source,"rw");
            // Пусть __MAGIC__ - метка, показывающая, что на следующей строке следует писать код
            while(!file.readLine().contains("__MAGIC__"))
                ; // while пустой преднамеренно! Мы просто ищем поицию
            // Следующие несколько строк кода нужны для корректной перезаписи функции
            Long pointer = file.getFilePointer();
            // Имитируем стирание линии.
            file.writeBytes(spaceString);
            file.seek(pointer);
            file.writeBytes(code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    String spaceString = "/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////";
}
