import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableList;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericArray;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.rakam.TestingConfigManager;
import org.rakam.analysis.JDBCPoolDataSource;
import org.rakam.collection.JSCodeLoggerService;
import org.rakam.collection.util.JSCodeCompiler;
import org.rakam.config.JDBCConfig;
import org.rakam.plugin.RAsyncHttpClient;
import org.rakam.recipe.Recipe;
import org.testng.annotations.Test;

import javax.script.Invocable;
import javax.script.ScriptException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class RecipeConfigTest {
//    @Test
    public void test() throws IOException {
        InputStream io = getClass().getResourceAsStream("recipes/ecommerce_test.yml");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Recipe recipe = mapper.readValue(io, Recipe.class);
    }

    @Test
    public void test2() {
        Schema a = Schema.createRecord(ImmutableList.of(
                new Schema.Field("b", Schema.create(Schema.Type.NULL), "", null),
                new Schema.Field("a", Schema.create(Schema.Type.BOOLEAN), "", null)));
        GenericData.Record record = new GenericData.Record(a);
        record.put("a", false);

        DatumWriter writer = new GenericDatumWriter(a);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().directBinaryEncoder(out, null);

        try {
            writer.write(record, encoder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Schema b = Schema.createRecord(ImmutableList.of(new Schema.Field("a", Schema.create(Schema.Type.BOOLEAN), "", null)));
        GenericDatumReader<Object> reader = new GenericDatumReader<>(b);
        BinaryDecoder decoder = DecoderFactory.get().directBinaryDecoder(new ByteArrayInputStream(out.toByteArray()), null);

        Object read;
        try {
            read = reader.read(null, decoder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(read);

    }
}
