package com.gersion.superlock.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/***
 * Gson 工具类
 */
public final class GsonHelper {

    /**
     * Json 解析器
     */
    private static final JsonParser PARSER = new JsonParser();

    private static Gson gson = null;

    /**
     * 设置一个通用的Gson
     */
    public static void setGson(Gson gs) {
        gson = gs;
    }

    public static Gson getGson() {

        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            builder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
            gson = builder.create();
        }
        return gson;
    }

    /**
     * 获取Json对象里面的键, 并将其值以String返回.<br/>
     * 当值为json对象或者json数组时,返回其json格式的字符串
     */
    public static String getString(String json, String key) {
        try {
            JsonElement element = PARSER.parse(json);
            if (element.isJsonObject()) {
                JsonObject obj = element.getAsJsonObject();
                JsonElement value = obj.get(key);
                if (value != null) {
                    if (value instanceof JsonPrimitive) {
                        return value.getAsString();
                    } else if (value instanceof JsonNull) {
                        return null;
                    } else {
                        return value.toString();
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject getJsonObject(String json) {
        JsonElement element = PARSER.parse(json);
        return element.getAsJsonObject();
    }

    public static String getString(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value != null) {
            if (value instanceof JsonPrimitive) {
                return value.getAsString();
            } else if (value instanceof JsonNull) {
                return null;
            } else {
                return value.toString();
            }
        }
        return null;
    }

    public static int getInt(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value != null) {
            if (value instanceof JsonPrimitive) {
                return Integer.parseInt(value.toString());
            } else if (value instanceof JsonNull) {
                return -1;
            } else {
                return Integer.parseInt(value.toString());
            }
        }
        return -1;
    }

    public static boolean getBoolean(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value != null) {
            if (value instanceof JsonPrimitive) {
                return value.getAsBoolean();
            } else if (value instanceof JsonNull) {
                return false;
            } else {
                return false;
            }
        }
        return false;
    }

    public static float getFloat(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value != null) {
            if (value instanceof JsonPrimitive) {
                return Float.parseFloat(value.toString());
            } else if (value instanceof JsonNull) {
                return 0f;
            } else {
                return Float.parseFloat(value.toString());
            }
        }
        return 0f;
    }

    public static long getLong(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value != null) {
            if (value instanceof JsonPrimitive) {
                return value.getAsLong();
            } else if (value instanceof JsonNull) {
                return -1L;
            } else {
                return Long.parseLong(value.toString());
            }
        }
        return -1L;
    }

    /**
     * 获取Json对象里面的键, 并将其值以String返回.<br/>
     * 当值为json对象或者json数组时,返回其json格式的字符串
     */
    public static boolean getBolean(String json, String key) {
        JsonElement element = PARSER.parse(json);
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            JsonElement value = obj.get(key);
            if (value != null) {
                if (value instanceof JsonPrimitive) {
                    return value.getAsBoolean();
                } else if (value instanceof JsonNull) {
                    return false;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static int getInt(String json, String key) {
        JsonElement element = PARSER.parse(json);
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            JsonElement value = obj.get(key);
            if (value != null) {
                if (value instanceof JsonPrimitive) {
                    return Integer.parseInt(value.toString());
                } else if (value instanceof JsonNull) {
                    return 0;
                } else {
                    return Integer.parseInt(value.toString());
                }
            }
        }
        return 0;
    }

    public static float getFloat(String json, String key) {
        JsonElement element = PARSER.parse(json);
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            JsonElement value = obj.get(key);
            if (value != null) {
                if (value instanceof JsonPrimitive) {
                    return Float.parseFloat(value.toString());
                } else if (value instanceof JsonNull) {
                    return 0;
                } else {
                    return Float.parseFloat(value.toString());
                }
            }
        }
        return 0;
    }

    /**
     * 将json解析为一个JsonObject
     */
    public static JsonObject toJsonObject(String json) {
        JsonElement element = PARSER.parse(json);
        return element.getAsJsonObject();
    }

    /**
     * 将json解析为一个JsonArray
     */
    public static JsonArray toJsonArray(String json) {
        JsonElement element = PARSER.parse(json);
        return element.getAsJsonArray();
    }

    /**
     * 将json对象序列化为一个对象实例
     *
     * @param clss 要序列化为的对象
     */
    public static <T> T toObject(String json, Class<T> clss) {
        Gson gson = getGson();
        return gson.fromJson(json, clss);
    }

    /**
     * 将json数组序列化为一个对象的List实例
     *
     * @param clss 对象类型
     */
    public static <T> List<T> toList(String json, Class<T> clss) {
        JsonElement element = PARSER.parse(json);
        if (element.isJsonArray()) {
            Gson gson = getGson();
            JsonArray array = element.getAsJsonArray();
            int size = array.size();
            ArrayList<T> result = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                JsonElement item = array.get(i);
                T t = gson.fromJson(item, clss);
                result.add(t);
            }
            return result;
        }
        return null;
    }

    /***
     * 将对象转换成相对应的字符串
     *
     * @return json字符串
     */
    public static String toJsonFromBean(Object obj) {
        try {
            Gson gson = getGson();
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 将list集合转换成相对应的字符串
     *
     * @param lists
     *            需要转换的list集合
     * @return
     */
    public static <T> String toJsonFromList(List<T> lists) {
        try {
            Gson gson = getGson();
            TypeToken<List<Object>> type = new TypeToken<List<Object>>() {
            };
            return gson.toJson(lists, type.getType());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 将map集合转换成相对应的字符串
     */
    public static <T> String toJsonFromMap(Map<String, T> map) {
        try {
            Gson gson = getGson();
            TypeToken<Map<String, Object>> type = new TypeToken<Map<String, Object>>() {
            };
            return gson.toJson(map, type.getType());
        } catch (Exception e) {
            Logger.d(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将list<map<>>集合转换成相对应的字符串
     */
    public static <T> String toJsonFromListMap(List<Map<String, T>> listMaps) {
        try {
            Gson gson = getGson();
            TypeToken<List<Map<String, T>>> type = new TypeToken<List<Map<String, T>>>() {
            };
            return gson.toJson(listMaps, type.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json转换成对象
     */
    public static <T> T fromJsontoBean(String result, Class<T> clz) {
        Gson gson = getGson();
        return gson.fromJson(result, clz);
    }

    /**
     * 将json转换成list
     */
    public static <T> List<T> fromJsonToList(String result) {
        try {
            Gson gson = getGson();
            TypeToken<List<Object>> type = new TypeToken<List<Object>>() {
            };
            return gson.fromJson(result, type.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 将json转换成map
     *
     * @param result
     * @return
     */
    public static Map<String, Object> fromJsonToMap(String result) {
        try {
            Gson gson = getGson();
            TypeToken<Map<String, Object>> type = new TypeToken<Map<String, Object>>() {
            };
            return gson.fromJson(result, type.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json转换成list<map<>>
     */
    public static <T> List<Map<String, T>> fromJsonToListMap(String result) {
        try {
            Gson gson = getGson();
            TypeToken<List<Map<String, T>>> type = new TypeToken<List<Map<String, T>>>() {
            };
            return gson.fromJson(result, type.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

}
