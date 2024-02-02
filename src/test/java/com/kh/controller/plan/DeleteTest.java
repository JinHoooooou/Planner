package com.kh.controller.plan;

public class DeleteTest {

//  private PlanController planController;
//  private List<Plan> originals;
//
//  @BeforeEach
//  public void setUp() {
//    planController = new PlanController();
//    setOriginals(10);
//  }
//
//  private void setOriginals(int count) {
//    originals = new ArrayList<>();
//    Random random = new Random();
//    for (int i = 0; i < count; i++) {
//      String title = "title" + i;
//      int hour = random.nextInt(0, 12);
//      int minute = random.nextInt(0, 60);
//      int second = random.nextInt(0, 60);
//
//      originals.add(Plan.create(title, hour, minute, second));
//    }
//  }
//
//  @Test
//  public void deleteSuccessTest1() {
//    // 10개의 저장된 Timer가 있고 Valid한 index가 주어질 때 delete에 성공한다.
//
//    // Given: 10개의 Timer가 저장되어 있다.
//    addMockData(originals);
//    assert planController.size() == 10;
//    // And: valid한 index와 title이 주어진다.
//    int validIndex = 0;
//
//    // When: delete 메서드를 호출한다.
//    boolean actual = planController.delete(validIndex);
//
//    // Then: actual은 true이다.
//    assertThat(actual).isTrue();
//    // And: 0번째에 저장 된 Timer 객체는 originals의 1번째 저장된 Timer 객체와 같다.
//    assertEquals(planController.select(validIndex), originals.get(validIndex + 1));
//  }
//
//  @Test
//  public void deleteFailTest1() {
//    // 10개의 저장된 Timer가 있고 Invalid한 index가 주어질 때 delete에 실패한다.
//
//    // Given: 10개의 Timer가 저장되어 있다.
//    addMockData(originals);
//    assert planController.size() == 10;
//    // And: invalid index 주어진다.
//    int invalidIndex = -123;
//
//    // When: delete 메서드를 호출한다.
//    // Then: IndexOutOfBoundException이 발생한다.
//    assertThatThrownBy(() -> planController.delete(invalidIndex))
//        .isInstanceOf(IndexOutOfBoundsException.class);
//  }
//
//  @Test
//  public void deleteFailTest2() {
//    //저장된 Timer가 없을 때 delete에 실패한다.
//
//    // Given: 저장된 Timer가 없다.
//    assert planController.isEmpty();
//    // And: valid index 주어진다.
//    int validIndex = 0;
//
//    // When: delete 메서드를 호출한다.
//    // Then: IndexOutOfBoundException이 발생한다.
//    assertThatThrownBy(() -> planController.delete(validIndex))
//        .isInstanceOf(IndexOutOfBoundsException.class);
//  }
//
//  private void addMockData(List<Plan> originals) {
//    for (Plan target : originals) {
//      planController.create(target.getTitle(),
//          target.getHours(),
//          target.getMinutes(),
//          target.getSeconds());
//    }
//  }
//
//  private void assertEquals(Plan actual, Plan expected) {
//    assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
//    assertThat(actual.getHours()).isEqualTo(expected.getHours());
//    assertThat(actual.getMinutes()).isEqualTo(expected.getMinutes());
//    assertThat(actual.getSeconds()).isEqualTo(expected.getSeconds());
//  }
}
