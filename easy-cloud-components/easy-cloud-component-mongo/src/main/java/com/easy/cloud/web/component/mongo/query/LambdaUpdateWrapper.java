package com.easy.cloud.web.component.mongo.query;

import com.easy.cloud.web.component.mongo.utils.MongodbColumUtil;
import com.easy.cloud.web.component.mongo.SFunction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.bson.Document;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.SerializationUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author GR
 * @date 2020-12-9 17:15
 */
public class LambdaUpdateWrapper extends Update {

  private boolean isolated = false;
  private Set<String> keysToUpdate = new HashSet<>();
  private Map<String, Object> modifierOps = new LinkedHashMap<>();
  private Map<String, PushOperatorBuilder> pushCommandBuilders = new LinkedHashMap<>(1);
//    private List<ArrayFilter> arrayFilters = new ArrayList<>();

  /**
   * 获取字段
   *
   * @param func
   * @return java.lang.String
   */
  private static <T> String getKey(SFunction<T, ?> func) {
    return MongodbColumUtil.getEntityClum(func);
  }

  /**
   * Static factory method to create an LambdaUpdateWrapper using the provided key
   *
   * @return
   */
  public static <T> LambdaUpdateWrapper update(SFunction<T, ?> func, @Nullable Object value) {
    return new LambdaUpdateWrapper().set(func, value);
  }

  /**
   * Creates an {@link Update} instance from the given {@link Document}. Allows to explicitly
   * exclude fields from making it into the created {@link Update} object. Note, that this will set
   * attributes directly and <em>not</em> use {@literal $set}. This means fields not given in the
   * {@link Document} will be nulled when executing the LambdaUpdateWrapper. To create an
   * only-updating {@link Update} instance of a {@link Document}, call {@link #set(String, Object)}
   * for each value in it.
   *
   * @param object  the source {@link Document} to create the LambdaUpdateWrapper from.
   * @param exclude the fields to exclude.
   * @return
   */
  public static LambdaUpdateWrapper fromDocument(Document object, String... exclude) {

    LambdaUpdateWrapper lambdaUpdateWrapper = new LambdaUpdateWrapper();
    List<String> excludeList = Arrays.asList(exclude);

    for (String key : object.keySet()) {

      if (excludeList.contains(key)) {
        continue;
      }

      Object value = object.get(key);
      lambdaUpdateWrapper.modifierOps.put(key, value);
      if (isKeyword(key) && value instanceof Document) {
        lambdaUpdateWrapper.keysToUpdate.addAll(((Document) value).keySet());
      } else {
        lambdaUpdateWrapper.keysToUpdate.add(key);
      }
    }

    return lambdaUpdateWrapper;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $set} LambdaUpdateWrapper modifier
   *
   * @param value can be {@literal null}. In this case the property remains in the db with a
   *              {@literal null} value. To remove it use {@link #unset(String)}.
   * @return this.
   * @see <a href="https://docs.mongodb.com/manual/reference/operator/update/set/">MongoDB
   * LambdaUpdateWrapper operator: $set</a>
   */
  public <T> LambdaUpdateWrapper set(SFunction<T, ?> func, @Nullable Object value) {
    addMultiFieldOperation("$set", func, value);
    return this;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $setOnInsert} LambdaUpdateWrapper modifier
   *
   * @param value can be {@literal null}.
   * @return
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/setOnInsert/">MongoDB
   * LambdaUpdateWrapper operator:
   * $setOnInsert</a>
   */
  public <T> LambdaUpdateWrapper setOnInsert(SFunction<T, ?> func, @Nullable Object value) {
    addMultiFieldOperation("$setOnInsert", func, value);
    return this;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $unset} LambdaUpdateWrapper modifier
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/unset/">MongoDB
   * LambdaUpdateWrapper operator: $unset</a>
   */
  public <T> LambdaUpdateWrapper unset(SFunction<T, ?> func) {
    addMultiFieldOperation("$unset", func, 1);
    return this;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $inc} LambdaUpdateWrapper modifier
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/inc/">MongoDB
   * LambdaUpdateWrapper operator: $inc</a>
   */
  public <T> LambdaUpdateWrapper inc(SFunction<T, ?> func, Number inc) {
    addMultiFieldOperation("$inc", func, inc);
    return this;
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.data.mongodb.core.query.UpdateDefinition#inc()
   */
  public <T> void inc(SFunction<T, ?> func) {
    inc(func, 1L);
  }

  /**
   * LambdaUpdateWrapper using the {@literal $push} LambdaUpdateWrapper modifier
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/push/">MongoDB
   * LambdaUpdateWrapper operator: $push</a>
   */
  public <T> LambdaUpdateWrapper push(SFunction<T, ?> func, @Nullable Object value) {
    addMultiFieldOperation("$push", func, value);
    return this;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $push} LambdaUpdateWrapper modifier
   *
   * @param key
   * @param value
   * @return
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/push/">MongoDB
   * LambdaUpdateWrapper operator: $push</a>
   */
  @Override
  public LambdaUpdateWrapper push(String key, @Nullable Object value) {
    addMultiFieldOperation("$push", key, value);
    return this;
  }

  /**
   * LambdaUpdateWrapper using {@code $push} modifier. <br/>
   * Allows creation of {@code $push} command for single or multiple (using {@code $each}) values as well as using
   * {@code $position}.
   *
   * @param key
   * @return {@link PushOperatorBuilder} for given key
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/push/">MongoDB LambdaUpdateWrapper operator: $push</a>
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/each/">MongoDB LambdaUpdateWrapper operator: $each</a>
   */
//    public LambdaUpdateWrapper.PushOperatorBuilder push(SFunction<T, ?> func) {
//        if (!pushCommandBuilders.containsKey(key)) {
//            pushCommandBuilders.put(key, new PushOperatorBuilder(key));
//        }
//        return pushCommandBuilders.get(key);
//    }

  /**
   * LambdaUpdateWrapper using the {@code $pushAll} LambdaUpdateWrapper modifier. <br>
   * <b>Note</b>: In MongoDB 2.4 the usage of {@code $pushAll} has been deprecated in favor of
   * {@code $push $each}.
   * <b>Important:</b> As of MongoDB 3.6 {@code $pushAll} is not longer supported. Use {@code $push
   * $each} instead.
   * {@link #push(String)}) returns a builder that can be used to populate the {@code $each}
   * object.
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/pushAll/">MongoDB
   * LambdaUpdateWrapper operator:
   * $pushAll</a>
   * @deprecated as of MongoDB 2.4. Removed in MongoDB 3.6. Use {@link #push(String) $push $each}
   * instead.
   */
  @Deprecated
  public <T> LambdaUpdateWrapper pushAll(SFunction<T, ?> func, Object[] values) {
    addMultiFieldOperation("$pushAll", func, Arrays.asList(values));
    return this;
  }

  /**
   * LambdaUpdateWrapper using {@code $addToSet} modifier. <br/> Allows creation of {@code $push}
   * command for single or multiple (using {@code $each}) values
   */
  public <T> AddToSetBuilder addToSet(SFunction<T, ?> func) {
    return new AddToSetBuilder(getKey(func));
  }

  /**
   * LambdaUpdateWrapper using the {@literal $addToSet} LambdaUpdateWrapper modifier
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/addToSet/">MongoDB
   * LambdaUpdateWrapper operator:
   * $addToSet</a>
   */
  public <T> LambdaUpdateWrapper addToSet(SFunction<T, ?> func, @Nullable Object value) {
    addMultiFieldOperation("$addToSet", func, value);
    return this;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $pop} LambdaUpdateWrapper modifier
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/pop/">MongoDB
   * LambdaUpdateWrapper operator: $pop</a>
   */
  public <T> LambdaUpdateWrapper pop(SFunction<T, ?> func, Position pos) {
    addMultiFieldOperation("$pop", getKey(func), pos == Position.FIRST ? -1 : 1);
    return this;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $pull} LambdaUpdateWrapper modifier
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/pull/">MongoDB
   * LambdaUpdateWrapper operator: $pull</a>
   */
  public <T> LambdaUpdateWrapper pull(SFunction<T, ?> func, @Nullable Object value) {
    addMultiFieldOperation("$pull", func, value);
    return this;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $pullAll} LambdaUpdateWrapper modifier
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/pullAll/">MongoDB
   * LambdaUpdateWrapper operator:
   * $pullAll</a>
   */
  public <T> LambdaUpdateWrapper pullAll(SFunction<T, ?> func, Object[] values) {
    addMultiFieldOperation("$pullAll", func, Arrays.asList(values));
    return this;
  }

  /**
   * LambdaUpdateWrapper using the {@literal $rename} LambdaUpdateWrapper modifier
   *
   * @return
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/rename/">MongoDB
   * LambdaUpdateWrapper operator:
   * $rename</a>
   */
  public <T> LambdaUpdateWrapper rename(SFunction<T, ?> oldNameFunc, SFunction<T, ?> newNameFunc) {
    addMultiFieldOperation("$rename", oldNameFunc, getKey(newNameFunc));
    return this;
  }

  /**
   * LambdaUpdateWrapper given key to current date using {@literal $currentDate} modifier.
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/currentDate/">MongoDB
   * LambdaUpdateWrapper operator:
   * $currentDate</a>
   * @since 1.6
   */
  public <T> LambdaUpdateWrapper currentDate(SFunction<T, ?> func) {

    addMultiFieldOperation("$currentDate", func, true);
    return this;
  }

  /**
   * LambdaUpdateWrapper given key to current date using {@literal $currentDate : &#123; $type :
   * "timestamp" &#125;} modifier.
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/currentDate/">MongoDB
   * LambdaUpdateWrapper operator:
   * $currentDate</a>
   * @since 1.6
   */
  public <T> LambdaUpdateWrapper currentTimestamp(SFunction<T, ?> func) {

    addMultiFieldOperation("$currentDate", func, new Document("$type", "timestamp"));
    return this;
  }

  /**
   * Multiply the value of given key by the given number.
   *
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/mul/">MongoDB
   * LambdaUpdateWrapper operator: $mul</a>
   * @since 1.7
   */
  public <T> LambdaUpdateWrapper multiply(SFunction<T, ?> func, Number multiplier) {

    Assert.notNull(multiplier, "Multiplier must not be null.");
    addMultiFieldOperation("$mul", func, multiplier.doubleValue());
    return this;
  }

  /**
   * LambdaUpdateWrapper given key to the {@code value} if the {@code value} is greater than the
   * current value of the field.
   *
   * @see <a href="https://docs.mongodb.com/manual/reference/bson-type-comparison-order/">Comparison/Sort
   * Order</a>
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/max/">MongoDB
   * LambdaUpdateWrapper operator: $max</a>
   * @since 1.10
   */
  public <T> LambdaUpdateWrapper max(SFunction<T, ?> func, Object value) {

    Assert.notNull(value, "Value for max operation must not be null.");
    addMultiFieldOperation("$max", func, value);
    return this;
  }

  /**
   * LambdaUpdateWrapper given key to the {@code value} if the {@code value} is less than the
   * current value of the field.
   *
   * @see <a href="https://docs.mongodb.com/manual/reference/bson-type-comparison-order/">Comparison/Sort
   * Order</a>
   * @see <a href="https://docs.mongodb.org/manual/reference/operator/update/min/">MongoDB
   * LambdaUpdateWrapper operator: $min</a>
   * @since 1.10
   */
  public <T> LambdaUpdateWrapper min(SFunction<T, ?> func, Object value) {

    Assert.notNull(value, "Value for min operation must not be null.");
    addMultiFieldOperation("$min", func, value);
    return this;
  }

  /**
   * Prevents a write operation that affects <strong>multiple</strong> documents from yielding to
   * other reads or writes once the first document is written. <br /> Use with {@link
   * org.springframework.data.mongodb.core.MongoOperations#updateMulti(Query, Update, Class)}.
   *
   * @return never {@literal null}.
   * @since 2.0
   */
  public LambdaUpdateWrapper isolated() {

    isolated = true;
    return this;
  }

//    /**
//     * Filter elements in an array that match the given criteria for LambdaUpdateWrapper. {@link CriteriaDefinition} is passed directly
//     * to the driver without further type or field mapping.
//     *
//     * @param criteria must not be {@literal null}.
//     * @return this.
//     * @since 2.2
//     */
//    public LambdaUpdateWrapper filterArray(CriteriaDefinition criteria) {
//
//        this.arrayFilters.add(criteria::getCriteriaObject);
//        return this;
//    }
//
//    /**
//     * Filter elements in an array that match the given criteria for LambdaUpdateWrapper. {@code expression} is used directly with the
//     * driver without further further type or field mapping.
//     *
//     * @param identifier the positional operator identifier filter criteria name.
//     * @param expression the positional operator filter expression.
//     * @return this.
//     * @since 2.2
//     */
//    public LambdaUpdateWrapper filterArray(String identifier, Object expression) {
//
//        this.arrayFilters.add(() -> new Document(identifier, expression));
//        return this;
//    }

  /*
   * (non-Javadoc)
   * @see org.springframework.data.mongodb.core.query.UpdateDefinition#isIsolated()
   */
  public Boolean isIsolated() {
    return isolated;
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.data.mongodb.core.query.UpdateDefinition#getUpdateObject()
   */
  public Document getUpdateObject() {
    return new Document(modifierOps);
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.data.mongodb.core.query.UpdateDefinition#getArrayFilters()
   */
//    public List<ArrayFilter> getArrayFilters() {
//        return Collections.unmodifiableList(this.arrayFilters);
//    }

  /**
   * This method is not called anymore rather override {@link #addMultiFieldOperation(String,
   * String, Object)}.
   *
   * @deprectaed Use {@link #addMultiFieldOperation(String, String, Object)} instead.
   */
  @Deprecated
  protected <T> void addFieldOperation(String operator, SFunction<T, ?> func, Object value) {
    String key = getKey(func);
    Assert.hasText(key, "Key/Path for LambdaUpdateWrapper must not be null or blank.");

    modifierOps.put(operator, new Document(key, value));
    this.keysToUpdate.add(key);
  }

  protected <T> void addMultiFieldOperation(String operator, SFunction<T, ?> func,
      @Nullable Object value) {
    String key = getKey(func);
    Assert.hasText(key, "Key/Path for LambdaUpdateWrapper must not be null or blank.");
    Object existingValue = this.modifierOps.get(operator);
    Document keyValueMap;

    if (existingValue == null) {
      keyValueMap = new Document();
      this.modifierOps.put(operator, keyValueMap);
    } else {
      if (existingValue instanceof Document) {
        keyValueMap = (Document) existingValue;
      } else {
        throw new InvalidDataAccessApiUsageException(
            "Modifier Operations should be a LinkedHashMap but was " + existingValue.getClass());
      }
    }

    keyValueMap.put(key, value);
    this.keysToUpdate.add(key);
  }

  /**
   * Determine if a given {@code key} will be touched on execution.
   */
  public <T> boolean modifies(SFunction<T, ?> func) {
    return this.keysToUpdate.contains(getKey(func));
  }

  /**
   * Inspects given {@code key} for '$'.
   *
   * @param key
   * @return
   */
  private static boolean isKeyword(String key) {
    return StringUtils.startsWithIgnoreCase(key, "$");
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(getUpdateObject(), isolated);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    LambdaUpdateWrapper that = (LambdaUpdateWrapper) obj;
    if (this.isolated != that.isolated) {
      return false;
    }

    return Objects.equals(this.getUpdateObject(), that.getUpdateObject());
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    Document doc = getUpdateObject();

    if (isIsolated()) {
      doc.append("$isolated", 1);
    }

    return SerializationUtils.serializeToJsonSafely(doc);
  }

  /**
   * Modifiers holds a distinct collection of {@link Modifier}
   *
   * @author Christoph Strobl
   * @author Thomas Darimont
   */
  public static class Modifiers {

    private Map<String, Modifier> modifiers;

    public Modifiers() {
      this.modifiers = new LinkedHashMap<>(1);
    }

    public Collection<Modifier> getModifiers() {
      return Collections.unmodifiableCollection(this.modifiers.values());
    }

    public void addModifier(Modifier modifier) {
      this.modifiers.put(modifier.getKey(), modifier);
    }

    /**
     * @return true if no modifiers present.
     * @since 2.0
     */
    public boolean isEmpty() {
      return modifiers.isEmpty();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return Objects.hashCode(modifiers);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

      if (this == obj) {
        return true;
      }

      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }

      Modifiers that = (Modifiers) obj;
      return Objects.equals(this.modifiers, that.modifiers);
    }

    @Override
    public String toString() {
      return SerializationUtils.serializeToJsonSafely(this.modifiers);
    }
  }

  /**
   * Marker interface of nested commands.
   *
   * @author Christoph Strobl
   */
  public interface Modifier {

    /**
     * @return the command to send eg. {@code $push}
     */
    String getKey();

    /**
     * @return value to be sent with command
     */
    Object getValue();

    /**
     * @return a safely serialized JSON representation.
     * @since 2.0
     */
    default String toJsonString() {
      return SerializationUtils
          .serializeToJsonSafely(Collections.singletonMap(getKey(), getValue()));
    }
  }

  /**
   * Abstract {@link Modifier} implementation with defaults for {@link Object#equals(Object)},
   * {@link Object#hashCode()} and {@link Object#toString()}.
   *
   * @author Christoph Strobl
   * @since 2.0
   */
  private static abstract class AbstractModifier implements Modifier {

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return ObjectUtils.nullSafeHashCode(getKey()) + ObjectUtils.nullSafeHashCode(getValue());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {

      if (this == that) {
        return true;
      }

      if (that == null || getClass() != that.getClass()) {
        return false;
      }

      if (!Objects.equals(getKey(), ((Modifier) that).getKey())) {
        return false;
      }

      return Objects.deepEquals(getValue(), ((Modifier) that).getValue());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return toJsonString();
    }
  }

  /**
   * Implementation of {@link Modifier} representing {@code $each}.
   *
   * @author Christoph Strobl
   * @author Thomas Darimont
   */
  private static class Each extends AbstractModifier {

    private Object[] values;

    Each(Object... values) {
      this.values = extractValues(values);
    }

    private Object[] extractValues(Object[] values) {

      if (values == null || values.length == 0) {
        return values;
      }

      if (values.length == 1 && values[0] instanceof Collection) {
        return ((Collection<?>) values[0]).toArray();
      }

      return Arrays.copyOf(values, values.length);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.mongodb.core.query.LambdaUpdateWrapper.Modifier#getKey()
     */
    @Override
    public String getKey() {
      return "$each";
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.mongodb.core.query.LambdaUpdateWrapper.Modifier#getValue()
     */
    @Override
    public Object getValue() {
      return this.values;
    }
  }

  /**
   * {@link Modifier} implementation used to propagate {@code $position}.
   *
   * @author Christoph Strobl
   * @since 1.7
   */
  private static class PositionModifier extends AbstractModifier {

    private final int position;

    PositionModifier(int position) {
      this.position = position;
    }

    @Override
    public String getKey() {
      return "$position";
    }

    @Override
    public Object getValue() {
      return position;
    }
  }

  /**
   * Implementation of {@link Modifier} representing {@code $slice}.
   *
   * @author Mark Paluch
   * @since 1.10
   */
  private static class Slice extends AbstractModifier {

    private int count;

    Slice(int count) {
      this.count = count;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.mongodb.core.query.LambdaUpdateWrapper.Modifier#getKey()
     */
    @Override
    public String getKey() {
      return "$slice";
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.mongodb.core.query.LambdaUpdateWrapper.Modifier#getValue()
     */
    @Override
    public Object getValue() {
      return this.count;
    }
  }

  /**
   * Implementation of {@link Modifier} representing {@code $sort}.
   *
   * @author Pavel Vodrazka
   * @author Mark Paluch
   * @since 1.10
   */
  private static class SortModifier extends AbstractModifier {

    private final Object sort;

    /**
     * Creates a new {@link SortModifier} instance given {@link Sort.Direction}.
     *
     * @param direction must not be {@literal null}.
     */
    SortModifier(Sort.Direction direction) {

      Assert.notNull(direction, "Direction must not be null!");
      this.sort = direction.isAscending() ? 1 : -1;
    }

    /**
     * Creates a new {@link SortModifier} instance given {@link Sort}.
     *
     * @param sort must not be {@literal null}.
     */
    SortModifier(Sort sort) {

      Assert.notNull(sort, "Sort must not be null!");

      for (Sort.Order order : sort) {

        if (order.isIgnoreCase()) {
          throw new IllegalArgumentException(
              String.format("Given sort contained an Order for %s with ignore case! "
                      + "MongoDB does not support sorting ignoring case currently!",
                  order.getProperty()));
        }
      }

      this.sort = sort;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.mongodb.core.query.LambdaUpdateWrapper.Modifier#getKey()
     */
    @Override
    public String getKey() {
      return "$sort";
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.mongodb.core.query.LambdaUpdateWrapper.Modifier#getValue()
     */
    @Override
    public Object getValue() {
      return this.sort;
    }
  }

  /**
   * Builder for creating {@code $push} modifiers
   *
   * @author Christoph Strobl
   * @author Thomas Darimont
   */
  public class PushOperatorBuilder {

    private final String key;
    private final Modifiers modifiers;

    public PushOperatorBuilder(String key) {
      this.key = key;
      this.modifiers = new Modifiers();
    }

    /**
     * Propagates {@code $each} to {@code $push}
     *
     * @param values
     * @return never {@literal null}.
     */
    public LambdaUpdateWrapper each(Object... values) {

      this.modifiers.addModifier(new Each(values));
      return LambdaUpdateWrapper.this.push(key, this.modifiers);
    }

    /**
     * Propagates {@code $slice} to {@code $push}. {@code $slice} requires the {@code $each
     * operator}. <br /> If {@literal count} is zero, {@code $slice} updates the array to an empty
     * array. <br /> If {@literal count} is negative, {@code $slice} updates the array to contain
     * only the last {@code count} elements. <br /> If {@literal count} is positive, {@code $slice}
     * updates the array to contain only the first {@code count} elements. <br />
     *
     * @param count
     * @return never {@literal null}.
     * @since 1.10
     */
    public PushOperatorBuilder slice(int count) {

      this.modifiers.addModifier(new Slice(count));
      return this;
    }

    /**
     * Propagates {@code $sort} to {@code $push}. {@code $sort} requires the {@code $each} operator.
     * Forces elements to be sorted by values in given {@literal direction}.
     *
     * @param direction must not be {@literal null}.
     * @return never {@literal null}.
     * @since 1.10
     */
    public PushOperatorBuilder sort(Sort.Direction direction) {

      Assert.notNull(direction, "Direction must not be null.");
      this.modifiers.addModifier(new SortModifier(direction));
      return this;
    }

    /**
     * Propagates {@code $sort} to {@code $push}. {@code $sort} requires the {@code $each} operator.
     * Forces document elements to be sorted in given {@literal order}.
     *
     * @param sort must not be {@literal null}.
     * @return never {@literal null}.
     * @since 1.10
     */
    public PushOperatorBuilder sort(Sort sort) {

      Assert.notNull(sort, "Sort must not be null.");
      this.modifiers.addModifier(new SortModifier(sort));
      return this;
    }

    /**
     * Forces values to be added at the given {@literal position}.
     *
     * @param position the position offset. As of MongoDB 3.6 use a negative value to indicate
     *                 starting from the end, counting (but not including) the last element of the
     *                 array.
     * @return never {@literal null}.
     * @since 1.7
     */
    public PushOperatorBuilder atPosition(int position) {

      this.modifiers.addModifier(new PositionModifier(position));
      return this;
    }

    /**
     * Forces values to be added at given {@literal position}.
     *
     * @param position can be {@literal null} which will be appended at the last position.
     * @return never {@literal null}.
     * @since 1.7
     */
    public PushOperatorBuilder atPosition(@Nullable Position position) {

      if (position == null || Position.LAST.equals(position)) {
        return this;
      }

      this.modifiers.addModifier(new PositionModifier(0));

      return this;
    }

    /**
     * Propagates {@link #value(Object)} to {@code $push}
     *
     * @param value
     * @return never {@literal null}.
     */
    public LambdaUpdateWrapper value(Object value) {

      if (this.modifiers.isEmpty()) {
        return LambdaUpdateWrapper.this.push(key, value);
      }

      this.modifiers.addModifier(new Each(Collections.singletonList(value)));
      return LambdaUpdateWrapper.this.push(key, this.modifiers);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return Objects.hash(getOuterType(), key, modifiers);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

      if (this == obj) {
        return true;
      }

      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }

      PushOperatorBuilder that = (PushOperatorBuilder) obj;

      if (!Objects.equals(getOuterType(), that.getOuterType())) {
        return false;
      }

      return Objects.equals(this.key, that.key) && Objects.equals(this.modifiers, that.modifiers);
    }

    private LambdaUpdateWrapper getOuterType() {
      return LambdaUpdateWrapper.this;
    }
  }

}
