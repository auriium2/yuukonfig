package yuukonfig.toml;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import yuukonfig.core.Exceptions;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonfig.core.node.Sequence;
import yuukonstants.GenericPath;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TomlNodeFactory implements RawNodeFactory {
    @Override
    public SequenceBuilder makeSequenceBuilder() {
        return new SequenceBuilder() {

            final List<Node> internal = new ArrayList<>();

            @Override
            public void add(Node node) {
                internal.add(node);
            }

            @Override
            public Sequence build(String... aboveComment) {
                return new TomlSequence(internal);
            }
        };
    }

    @Override
    public MappingBuilder makeMappingBuilder() {
        return new MappingBuilder() {

            final Map<String, Node> internal = new HashMap<>();

            @Override
            public void add(String key, Node node) {
                internal.put(key, node);
            }

            @Override
            public Mapping build(String... aboveComment) {
                return new TomlMapping(internal);
            }
        };
    }


    @Override
    public Node scalarOf(Object data, String inlineComment, String... aboveComment) {
        return new TomlScalar(data);
    }

    @Override
    public Node scalarOf(Object data, String... aboveComment) { //comments disappear :(
        return new TomlScalar(data);
    }

    @Override
    public Mapping loadString(String simulatedConfigInStringForm) {
        return loadFromToml(new Toml().read(simulatedConfigInStringForm));
    }

    @Override
    public Mapping loadFromFile(Path path) {
        if (!path.toFile().exists() || !path.toFile().isFile()) {
            return new TomlMapping(new HashMap<>()); //empty
        } else {
            return loadFromToml(new Toml().read(path.toFile()));
        }

    }


    Mapping loadFromToml(Toml populatedToml) {
        var rootMap = populatedToml.toMap();
        return iterateMap(rootMap);
    }




    @SuppressWarnings("unchecked")
    private static Map<String, Node> iterateMergeOrdered(Map<String, Node> dominant, Map<String, Node> recessive, GenericPath path) {
        for (String key : dominant.keySet()) {
            Node newNode = dominant.get(key);
            Node oldNode = recessive.get(key);

            if (oldNode == null || oldNode.type() == Node.Type.NOT_PRESENT) { //no conflicts!
                recessive.put(key, dominant.get(key));

                continue;
            }

            if (oldNode.type() == Node.Type.SCALAR && newNode.type() == Node.Type.SCALAR) {
                recessive.put(key, dominant.get(key));

                continue;
            }

            if (newNode.type() == Node.Type.MAPPING && oldNode.type() == Node.Type.MAPPING) {
                //conflict. Both are maps tho, so merge!

                Map<String, Node> originalChild = (Map<String, Node>) oldNode.rawAccess(Map.class);
                Map<String, Node> newChild = (Map<String, Node>) newNode.rawAccess(Map.class);
                recessive.put(key, new TomlMapping(iterateMergeOrdered(originalChild, newChild, path)));

                continue;
            }
            if (newNode.type() == Node.Type.SEQUENCE && oldNode.type() == Node.Type.SEQUENCE) {
                //conflict. Both are lists, so merge!

                List<Node> originalChild = (List<Node>) oldNode.rawAccess(List.class);
                List<Node> newChild = (List<Node>) newNode.rawAccess(List.class);
                for (Node each : newChild) {
                    if (!originalChild.contains(each)) {
                        originalChild.add(each);
                    }
                }
                continue;
            }

            throw Exceptions.STRANGE_CONFIG_CONFLICT(oldNode.type().name(),newNode.type().name(), path);


        }
        return recessive;
    }


    Sequence iterateList(List<Object> list) {

        List<Node> toNode = new ArrayList<>();

        for (Object ob : list) {
            if (ob instanceof List<?>) {
                toNode.add(iterateList((List<Object>) ob));
                continue;
            }

            if (ob instanceof Map<?,?>) {
                toNode.add(iterateMap((Map<String, Object>) ob));
                continue;
            }

            toNode.add(new TomlScalar(ob));
            continue;
        }

        return new TomlSequence(toNode);
    }

    Mapping iterateMap(Map<String, Object> map) {
        Map<String, Node> toNode = new HashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();

            if (entry.getValue() instanceof List<?>) { //seqeuence
                List<Object> sublist = (List<Object>) entry.getValue();
                Sequence subnode = iterateList(sublist);
                toNode.put(key, subnode);
                continue;
            }
            if (entry.getValue() instanceof Map<?,?>) { //table
                Map<String, Object> subtable = (Map<String, Object>) entry.getValue();
                Mapping subnode = iterateMap(subtable);
                toNode.put(key, subnode);
                continue;
            }
            //primitive
            toNode.put(key, new TomlScalar( entry.getValue() )); //TODO this needs to be fixed
        }



        return new TomlMapping(toNode);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Node> iterateMerge(Map<String, Node> original, Map<String, Node> newMap, GenericPath path) {
        for (String key : newMap.keySet()) {
            Node newNode = newMap.get(key);
            Node oldNode = original.get(key);

            if (oldNode == null || oldNode.type() == Node.Type.NOT_PRESENT) { //no conflicts!
                original.put(key, newMap.get(key));

                continue;
            }

            if (newNode.type() == Node.Type.MAPPING && oldNode.type() == Node.Type.MAPPING) {
                //conflict. Both are maps tho, so merge!

                Map<String, Node> originalChild = (Map<String, Node>) oldNode.rawAccess(Map.class);
                Map<String, Node> newChild = (Map<String, Node>) newNode.rawAccess(Map.class);
                original.put(key, new TomlMapping(iterateMerge(originalChild, newChild, path)));

                continue;
            }
            if (newNode.type() == Node.Type.SEQUENCE && oldNode.type() == Node.Type.SEQUENCE) {
                //conflict. Both are lists, so merge!

                List<Node> originalChild = (List<Node>) oldNode.rawAccess(List.class);
                List<Node> newChild = (List<Node>) newNode.rawAccess(List.class);
                for (Node each : newChild) {
                    if (!originalChild.contains(each)) {
                        originalChild.add(each);
                    }
                }
                continue;
            }

            throw Exceptions.STRANGE_CONFIG_CONFLICT(oldNode.type().name(),newNode.type().name(), path);

            //conflict. Both are scalars, use the original from the original map

            //original.put(key, newMap.get(key));



        }
        return original;
    }

    @Override
    public Mapping mergeMappings(Mapping preserved, Mapping optimisticallyADded) {
        Map<String, Node> original = (Map<String,Node>) preserved.rawAccess(Map.class);
        Map<String, Node> secondary = (Map<String, Node>) optimisticallyADded.rawAccess(Map.class);

        Map<String, Node> map = iterateMerge(original, secondary, new GenericPath());
        return new TomlMapping(map);
    }

    @Override
    public Mapping mergeMappingsOrdered(Mapping dominant, Mapping recessive) {
        Map<String, Node> dominantM = (Map<String,Node>) dominant.rawAccess(Map.class);
        Map<String, Node> recessiveM = (Map<String, Node>) recessive.rawAccess(Map.class);

        Map<String, Node> map = iterateMerge(dominantM, recessiveM, new GenericPath());
        return new TomlMapping(map);
    }

    @Override
    public void writeToFile(Mapping toWrite, Path location) {
        //build origin map without toml maps


        Map<String, Object> inverse = toMap(toWrite);



        try {
            if (!location.toFile().exists()) {
                location.toFile().createNewFile();
            }

            new TomlWriter.Builder().indentTablesBy(0).indentValuesBy(0).build().write(inverse, location.toFile());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    public Map<String, Object> toMap(Mapping mapping) {
        Map<String,Node> mappingAsMap = (Map<String,Node>) mapping.rawAccess(Map.class);
        Map<String, Object> toReturn = new HashMap<>();

        for (Map.Entry<String, Node> e : mappingAsMap.entrySet()) {
            Node child = e.getValue();
            String key = e.getKey();

            if (child.type() == Node.Type.NOT_PRESENT) continue;

            if (child.type() == Node.Type.MAPPING) {
                toReturn.put(key, toMap(child.asMapping()));
                continue;
            }

            if (child.type() == Node.Type.SEQUENCE) {
                toReturn.put(key, toList(child.asSequence()));
                continue;
            }

            toReturn.put(key, child.asScalar().rawAccess(Object.class));
        }

        return toReturn;
    }

    public List<Object> toList(Sequence sequence) {
        List<Node> list = (List<Node>) sequence.rawAccess(List.class);
        List<Object> toReturn = new ArrayList<>();

        for (Node child : list) {

            if (child.type() == Node.Type.NOT_PRESENT) continue;

            if (child.type() == Node.Type.MAPPING) {
                toReturn.add(toMap(child.asMapping()));
                continue;
            }

            if (child.type() == Node.Type.SEQUENCE) {
                toReturn.add(toList(child.asSequence()));
                continue;
            }

            toReturn.add(child.asScalar().rawAccess(Object.class));

        }

        return toReturn;
    }
}
