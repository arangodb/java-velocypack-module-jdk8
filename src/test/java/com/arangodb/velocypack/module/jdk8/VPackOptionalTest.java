/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb.velocypack.module.jdk8;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.velocypack.VPack;
import com.arangodb.velocypack.VPackBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.ValueType;

/**
 * @author Mark - mark at arangodb.com
 *
 */
public class VPackOptionalTest {

	private static VPack vp;

	@BeforeClass
	public static void setup() {
		vp = new VPack.Builder().registerModule(new VPackJdk8Module()).build();
	}

	protected static class OptionalTestEntity {
		private Optional<String> s = Optional.empty();
		private Optional<Integer> i = Optional.empty();
		private Optional<OptionalTestEntity> o = Optional.empty();
		@SuppressWarnings("unused")
		private final Optional<String> e = Optional.empty();

		public OptionalTestEntity() {
			super();
		}
	}

	@Test
	public void serializeOptional() {
		final OptionalTestEntity entity = new OptionalTestEntity();
		entity.s = Optional.of("hello world");
		entity.i = Optional.of(69);
		entity.o = Optional.of(new OptionalTestEntity());

		final VPackSlice vpack = vp.serialize(entity);
		assertThat(vpack, is(notNullValue()));
		assertThat(vpack.isObject(), is(true));
		assertThat(vpack.get("s").isString(), is(true));
		assertThat(vpack.get("s").getAsString(), is("hello world"));
		assertThat(vpack.get("i").isInteger(), is(true));
		assertThat(vpack.get("i").getAsInt(), is(69));
		assertThat(vpack.get("o").isObject(), is(true));
		assertThat(vpack.get("e").isNone(), is(true));
	}

	@Test
	public void deserializeOptional() {
		final VPackBuilder builder = new VPackBuilder();
		builder.add(ValueType.OBJECT);
		builder.add("s", "hello world");
		builder.add("i", 69);
		builder.add("o", ValueType.OBJECT).close();
		builder.close();

		final OptionalTestEntity entity = vp.deserialize(builder.slice(), OptionalTestEntity.class);
		assertThat(entity, is(notNullValue()));
		assertThat(entity.s.isPresent(), is(true));
		assertThat(entity.s.get(), is("hello world"));
		assertThat(entity.i.isPresent(), is(true));
		assertThat(entity.i.get(), is(69));
		assertThat(entity.o.isPresent(), is(true));
	}

	protected static class OptionalPrimitiveTestEntity {
		private OptionalDouble d = OptionalDouble.empty();
		private OptionalInt i = OptionalInt.empty();
		private OptionalLong l = OptionalLong.empty();

		public OptionalPrimitiveTestEntity() {
			super();
		}

	}

	@Test
	public void serializeOptionalPrimitive() {
		final OptionalPrimitiveTestEntity entity = new OptionalPrimitiveTestEntity();
		entity.d = OptionalDouble.of(69.5);
		entity.i = OptionalInt.of(69);
		entity.l = OptionalLong.of(69L);

		final VPackSlice vpack = vp.serialize(entity);
		assertThat(vpack, is(notNullValue()));
		assertThat(vpack.isObject(), is(true));
		assertThat(vpack.get("d").isDouble(), is(true));
		assertThat(vpack.get("d").getAsDouble(), is(69.5));
		assertThat(vpack.get("i").isInteger(), is(true));
		assertThat(vpack.get("i").getAsInt(), is(69));
		assertThat(vpack.get("l").isInteger(), is(true));
		assertThat(vpack.get("l").getAsLong(), is(69L));
	}

	@Test
	public void deserializeOptionalPrimitive() {
		final VPackBuilder builder = new VPackBuilder();
		builder.add(ValueType.OBJECT);
		builder.add("d", 69.5);
		builder.add("i", 69);
		builder.add("l", 69L);
		builder.close();

		final OptionalPrimitiveTestEntity entity = vp.deserialize(builder.slice(), OptionalPrimitiveTestEntity.class);
		assertThat(entity, is(notNullValue()));
		assertThat(entity.d.isPresent(), is(true));
		assertThat(entity.d.getAsDouble(), is(69.5));
		assertThat(entity.i.isPresent(), is(true));
		assertThat(entity.i.getAsInt(), is(69));
		assertThat(entity.l.isPresent(), is(true));
		assertThat(entity.l.getAsLong(), is(69L));
	}

	@Test
	public void serializeOptionalPrimitiveEmpty() {
		final OptionalPrimitiveTestEntity entity = new OptionalPrimitiveTestEntity();

		final VPackSlice vpack = vp.serialize(entity);
		assertThat(vpack, is(notNullValue()));
		assertThat(vpack.isObject(), is(true));
		assertThat(vpack.size(), is(0));
	}

	@Test
	public void deserializeOptionalPrimitiveEmpty() {
		final VPackBuilder builder = new VPackBuilder();
		builder.add(ValueType.OBJECT);
		builder.close();

		final OptionalPrimitiveTestEntity entity = vp.deserialize(builder.slice(), OptionalPrimitiveTestEntity.class);
		assertThat(entity, is(notNullValue()));
		assertThat(entity.d.isPresent(), is(false));
		assertThat(entity.i.isPresent(), is(false));
		assertThat(entity.l.isPresent(), is(false));
	}
}