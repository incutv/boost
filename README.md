# boost

### IntelliJ에서 실행 시 VM 옵션 설정 가이드

IntelliJ IDEA에서 본 프로젝트를 실행할 때, JVM 메모리 옵션을 설정해 주세요. 아래와 같이 `-Xms128m -Xmx512m` 옵션을 추가해야 합니다.

#### 설정 방법

1. 상단 메뉴에서 **Run > Edit Configurations...** 를 클릭합니다.
2. 실행하려는 설정(예: `Application`, `Spring Boot`)을 선택합니다.
3. **VM options** 항목에 아래 옵션을 입력합니다:

-Xms128m -Xmx512m

4. **Apply** 또는 **OK** 버튼을 눌러 저장한 후 실행합니다.

> 💡 해당 설정은 애플리케이션의 최소 및 최대 힙 메모리를 각각 128MB, 512MB로 설정하여 안정적인 실행을 돕습니다.
