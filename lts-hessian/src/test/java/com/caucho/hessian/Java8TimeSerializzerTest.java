package com.caucho.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.*;

/**
 * author: Zhang Li
 * created on 15-3-19.
 */
public class Java8TimeSerializzerTest {

    private static SerializerFactory factory;
    private static ByteArrayOutputStream os;

    @BeforeClass
    public static void setUp() {
        factory = new SerializerFactory(Thread.currentThread().getContextClassLoader());
        os = new ByteArrayOutputStream();
    }

    @Test
    public void test() throws IOException {
        testInstant();
        testDuration();
        testLocalDate();
        testLocalTime();

        testJava8Time(LocalDateTime.now());
        testJava8Time(Year.now());
        testJava8Time(YearMonth.now());
        testJava8Time(MonthDay.now());
        testJava8Time(Period.ofDays(3));


        testJava8Time(OffsetTime.now());

        testJava8Time(ZoneOffset.ofHours(8));
        testJava8Time(OffsetDateTime.now());
        testJava8Time(ZonedDateTime.now());
    }

    @Test
    public void testNull() throws IOException {
        testJava8Time(null);
    }

    public void testInstant() throws IOException {
        //Serializer serializer = factory.getSerializer(Instant.class);
        //Deserializer deserializer = factory.getDeserializer(Instant.class);
        os.reset();
        Instant instant = Instant.now();
        Hessian2Output output = new Hessian2Output(os);
        output.setSerializerFactory(factory);
        output.writeObject(instant);
        output.flush();
        //bytes = os.toByteArray();

        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        Hessian2Input input = new Hessian2Input(is);
        input.setSerializerFactory(factory);
        Instant dobj = (Instant)input.readObject();

        Assert.assertTrue(dobj.equals(instant));
    }

    public void testDuration() throws IOException {
        os.reset();
        Duration d = Duration.ofDays(2);
        Hessian2Output output = new Hessian2Output(os);
        output.setSerializerFactory(factory);
        output.writeObject(d);
        output.flush();
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        Hessian2Input input = new Hessian2Input(is);
        input.setSerializerFactory(factory);
        Duration dd = (Duration) input.readObject();

        Assert.assertTrue(d.equals(dd));
    }

    public void testLocalDate() throws IOException {
        os.reset();
        LocalDate d = LocalDate.now();
        Hessian2Output output = new Hessian2Output(os);
        output.setSerializerFactory(factory);
        output.writeObject(d);
        output.flush();
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        Hessian2Input input = new Hessian2Input(is);
        input.setSerializerFactory(factory);
        LocalDate dd = (LocalDate) input.readObject();

        Assert.assertTrue(d.equals(dd));
    }

    public void testLocalTime() throws IOException {
        os.reset();
        LocalTime d = LocalTime.now();
        Hessian2Output output = new Hessian2Output(os);
        output.setSerializerFactory(factory);
        output.writeObject(d);
        output.flush();
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        Hessian2Input input = new Hessian2Input(is);
        input.setSerializerFactory(factory);
        LocalTime dd = (LocalTime) input.readObject();

        Assert.assertTrue(d.equals(dd));
    }

    public void testJava8Time(Object t) throws IOException {
        os.reset();
        Hessian2Output output = new Hessian2Output(os);
        output.setSerializerFactory(factory);
        output.writeObject(t);
        output.flush();
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        Hessian2Input input = new Hessian2Input(is);
        input.setSerializerFactory(factory);
        Object dd = input.readObject();

        if(t == null) {
            Assert.assertNull(dd);
        } else {
            Assert.assertTrue(t.equals(dd));
        }
    }
}
