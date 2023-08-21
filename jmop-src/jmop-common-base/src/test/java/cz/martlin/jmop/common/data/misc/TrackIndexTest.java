package cz.martlin.jmop.common.data.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class TrackIndexTest {

	@Test
	void testBasic() {
		TrackIndex first = TrackIndex.ofHuman(42);
		assertEquals(42, first.getHuman());
		
		TrackIndex second = TrackIndex.ofIndex(99);
		assertEquals(99, second.getIndex());
	}

	
	@Test
	void testListToMapAndBack() {
		List<String> inputList = List.of("foo", "bar", "baz", "aux");
		System.out.println(inputList);
		
		Map<TrackIndex, String> map = TrackIndex.map(inputList);
		System.out.println(map);
		
		assertEquals("foo", map.get(TrackIndex.ofIndex(0)));
		assertEquals("bar", map.get(TrackIndex.ofIndex(1)));
		assertEquals("baz", map.get(TrackIndex.ofIndex(2)));
		assertEquals("aux", map.get(TrackIndex.ofIndex(3)));
		
		List<String> outputList = TrackIndex.list(map);
		System.out.println(outputList);
		
		assertEquals("foo", outputList.get(0));
		assertEquals("bar", outputList.get(1));
		assertEquals("baz", outputList.get(2));
		assertEquals("aux", outputList.get(3));
	}

	@Test
	void testMapToListFailurar() {
		Map<TrackIndex, String> map = Map.of(TrackIndex.ofIndex(0), "lorem", TrackIndex.ofIndex(2), "ipsum");
		System.out.println(map);
		
		assertEquals(Arrays.asList("lorem", "ipsum"), TrackIndex.list(map));
	}
	
}
