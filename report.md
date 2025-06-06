# گزارش بازآرایی کد

در این تمرین، ۷ مورد بازآرایی (Refactoring) روی پروژه انجام شد. در ادامه، هر مورد به همراه توضیح مختصر آورده شده است:

1. **الگوی Facade (گراف)**
   - یک کلاس GraphFacade ایجاد شد تا عملیات افزودن نود و یال به گراف را ساده کند و رابطی ساده برای کار با گراف ارائه دهد.
   - این کار باعث پنهان‌سازی پیچیدگی‌های داخلی گراف و سهولت استفاده از آن شد.

2. **الگوی Facade (تراورس گراف)**
   - کلاس GraphTraversalFacade برای ساده‌سازی دسترسی به همسایه‌ها و همسایه‌های وزندار یک نود ایجاد شد.
   - این کلاس رابطی ساده برای عملیات پیمایش گراف فراهم می‌کند.

3. **استفاده از Polymorphism به جای شرط**
   - تبدیل switch روی SymbolType به متد polymorphic با افزودن متد toVarType() در SymbolType.
   - این کار باعث حذف شرط‌ها و افزایش خوانایی و توسعه‌پذیری کد شد.

4. **Separate Query From Modifier**
   - در متد assign کلاس CodeGenerator، بررسی نوع متغیرها به متد جداگانه isAssignable منتقل شد.
   - این کار باعث جداسازی منطق بررسی (query) از منطق تغییر (modifier) شد.

5. **Self Encapsulated Field**
   - فیلد lastType در SymbolTable به صورت کامل کپسوله شد و دسترسی به آن فقط از طریق getter/setter انجام می‌شود.
   - این کار باعث افزایش انسجام و قابلیت کنترل بیشتر روی این فیلد شد.

6. **جایگزینی اعداد جادویی با ثابت‌های نام‌دار**
   - اعداد جادویی در کلاس Memory (مانند 500، 200 و 4) با ثابت‌های معنادار جایگزین شدند.
   - این کار خوانایی و نگهداری کد را بهبود داد.

7. **Introduce Parameter Object**
   - پارامترهای سازنده Method در SymbolTable در یک شیء جدید به نام MethodDescriptor گروه‌بندی شدند.
   - این کار باعث کاهش تعداد پارامترها و افزایش خوانایی و توسعه‌پذیری کد شد. 



# پاسخ‌های مختصر به سوالات کیفیت کد

## سوال ۱: تعریف مفاهیم کلیدی

**کد تمیز (Clean Code):** کدی که به راحتی قابل فهم، تغییر و نگهداری است و خودش منطق خود را توضیح می‌دهد.

**بدهی فنی (Technical Debt):** مصالحه در کیفیت کد یا ساختار سیستم برای تسریع تحویل که در صورت عدم بازپرداخت، هزینه‌های آینده را افزایش می‌دهد.

**بوی بد کد (Code Smell):** نشانه سطحی در کد که احتمالاً نشان‌دهنده یک مشکل عمیق‌تر در طراحی است، اما کد همچنان کار می‌کند.

## سوال ۲: دسته‌بندی بوهای بد کد (Refactoring.Guru)

1. **Bloaters (کدهای متورم):** قطعات کد، متدها و کلاس‌هایی که به طور غیرعادی بزرگ شده‌اند
2. **Object-Orientation Abusers (سوءاستفاده‌کنندگان از شی‌گرایی):** کاربرد ناقص یا نادرست اصول برنامه‌نویسی شی‌گرا
3. **Change Preventers (مانع‌شوندگان از تغییر):** ایجاد یک تغییر نیازمند تغییرات زیادی در بخش‌های دیگر است
4. **Dispensables (قابل حذف‌ها):** عناصر غیرضروری که حذف آن‌ها کد را تمیزتر می‌کند
5. **Couplers (کوپل‌کننده‌ها):** کوپلینگ بیش از حد بین کلاس‌ها یا تفویض اختیار بیش از حد

## سوال ۳: Feature Envy

**دسته‌بندی:** در دسته Couplers (کوپل‌کننده‌ها) قرار می‌گیرد

**بازآرایی‌های پیشنهادی:**
- **Move Method:** انتقال متد "حسود" به کلاسی که داده‌های آن را بیشتر دستکاری می‌کند
- **Extract Method:** اگر فقط بخشی از متد حسادت نشان می‌دهد
- **الگوهای طراحی:** Strategy یا Visitor برای سناریوهای پیچیده‌تر

**موارد نادیده گرفتن:**
- متدهای Utility که هیچ ارتباطی با داده‌های کلاس خود ندارند
- پیچیدگی بیش از حد ریفکتورینگ نسبت به مزایا
- محدودیت‌های زمانی/بودجه‌ای شدید
- دامنه‌های خاص که طبیعتاً نیاز به دسترسی گسترده دارند

## سوال ۴: تفاوت Code Smell و Bug

| ویژگی | Code Smell | Bug |
|--------|------------|-----|
| **تأثیر بر عملکرد** | کد همچنان کار می‌کند و کامپایل می‌شود | برنامه به درستی کار نمی‌کند یا از کار می‌افتد |
| **دیدپذیری برای کاربر** | نامرئی، عمدتاً توسط توسعه‌دهندگان حس می‌شود | مرئی، مستقیماً بر تجربه کاربر تأثیر می‌گذارد |
| **پیامدها** | کاهش خوانایی، دشواری نگهداری، افزایش بدهی فنی | عملکرد نادرست، کرش، نارضایتی کاربر |
| **راه حل** | بازآرایی (Refactoring) | رفع عیب (Debugging) و اصلاح |

## سوال ۵: 10 بوی بد کد در پروژه Convert_UML_to_ANSI_C

1. **Long Method:** توابع main طولانی یا پردازشگرهای پیچیده با صدها خط کد
2. **Large Class/God Object:** فایل‌های .c بزرگ با تعداد زیادی تابع نامرتبط
3. **Duplicate Code:** بلوک‌های تکراری برای تولید کد ANSI C یا توابع کپی شده
4. **Long Parameter List:** توابع با ده‌ها پارامتر برای تبدیل یا تولید کد
5. **Primitive Obsession:** پاس دادن چندین متغیر int و char* به جای struct
6. **Switch Statements:** بلوک‌های switch بزرگ برای مدیریت انواع عناصر UML
7. **Feature Envy:** توابع parser.c که مداوماً به داده‌های model_generator.h دسترسی دارند
8. **Comments (Bad):** کامنت‌های طولانی توضیح منطق بدیهی یا کامنت‌های قدیمی
9. **Dead Code:** توابع یا متغیرهایی که دیگر استفاده نمی‌شوند
10. **Shotgun Surgery:** اضافه کردن نوع جدید UML نیازمند تغییر در چندین فایل .c

## سوال ۶: پلاگین Formatter

**عملکرد:** قالب‌بندی خودکار کد با استفاده از Eclipse formatter برای حفظ سبک یکپارچه

**مزایا:**
- صرفه‌جویی در زمان و تلاش توسعه‌دهندگان
- افزایش خوانایی و کاهش اختلافات سبک کدنویسی
- ورود آسان‌تر اعضای جدید تیم
- حذف "نویز" بصری در Code Review

**رابطه با بازآرایی:**
- **مکمل، نه جایگزین:** فرمتر خودش ریفکتورینگ انجام نمی‌دهد
- **پشتیبان ریفکتورینگ:** محیط مساعد برای ریفکتورینگ ایجاد می‌کند
- **کاهش نویز:** به توسعه‌دهندگان اجازه تمرکز بر تغییرات ساختاری واقعی می‌دهد
- **افزایش اعتماد:** اطمینان از حفظ خوانایی پس از ریفکتورینگ